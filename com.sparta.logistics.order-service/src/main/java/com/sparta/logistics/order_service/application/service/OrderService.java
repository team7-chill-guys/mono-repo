package com.sparta.logistics.order_service.application.service;

import com.sparta.logistics.order_service.infrastructure.client.DeliveryClient;
import com.sparta.logistics.order_service.infrastructure.client.ProductClient;
import com.sparta.logistics.order_service.domain.Order;
import com.sparta.logistics.order_service.domain.OrderStatus;
import com.sparta.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sparta.logistics.order_service.infrastructure.client.dto.request.OrderDeliveryRequestDto;
import com.sparta.logistics.order_service.application.dto.request.OrderUpdateRequestDto;
import com.sparta.logistics.order_service.infrastructure.client.dto.request.ProductStockRequestDto;
import com.sparta.logistics.order_service.application.dto.response.OrderDetailResponseDto;
import com.sparta.logistics.order_service.application.dto.response.PageResponseDto;
import com.sparta.logistics.order_service.infrastructure.client.dto.response.StockUpdateResponseDto;
import com.sparta.logistics.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final DeliveryClient deliveryClient;

    @Transactional
    public OrderDetailResponseDto createOrder(OrderCreateRequestDto requestDto, String userIdHeader) {

        Long userId = Long.parseLong(userIdHeader); //변환
        UUID orderId = UUID.randomUUID(); // 주문 ID를 미리 생성

        // 상품 재고 확인 및 감소
        ProductStockRequestDto stockRequestDto = ProductStockRequestDto.builder()
                .productId(requestDto.getProductId())
                .quantity(requestDto.getQuantity())
                .build();

        StockUpdateResponseDto response = productClient.decreaseStock(requestDto.getProductId(), stockRequestDto);

        if (!response.isSuccess()) {
            throw new RuntimeException("Stock not available");
        }

        // 주문 ID 생성 및 배송 요청 DTO 변환
        OrderDeliveryRequestDto deliveryRequest = OrderDeliveryRequestDto.fromOrderRequest(requestDto, orderId);

        // 배송 ID 요청
        UUID deliveryId = deliveryClient.createDelivery(deliveryRequest, userIdHeader);

        Order order = Order.toEntity(requestDto, deliveryId, userId);

        orderRepository.save(order);

        return OrderDetailResponseDto.fromEntity(order);
    }

    // [조회]
    @Transactional(readOnly = true)
    public PageResponseDto<OrderDetailResponseDto> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByDeletedAtIsNull(pageable);
        return new PageResponseDto<>(orders.map(OrderDetailResponseDto::fromEntity));
    }

    // [개별 조회]
    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderById(UUID orderId) {
        return orderRepository.findByOrderIdAndDeletedAtIsNull(orderId)
                .map(OrderDetailResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // [주문 수정]
    @Transactional
    public OrderDetailResponseDto updateOrder(UUID id, OrderUpdateRequestDto updateDto, String userIdHeader) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        Long userId = Long.parseLong(userIdHeader); //변환

        order.updateOrder(updateDto.getStatus(), updateDto.getQuantity(), updateDto.getRequest(), userId);

        return OrderDetailResponseDto.fromEntity(order);
    }

    // [삭제]
    @Transactional
    public void deleteOrder(UUID id, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader); //변환

        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        // 주문 삭제 시 재고 복구 요청 (값만 전달)
        StockUpdateResponseDto response = productClient.increaseStock(order.getProductId(), order.getQuantity());

        if (!response.isSuccess()) {
            throw new RuntimeException("Stock restore failed");
        }

        // 주문 삭제 처리
        order.deleteOrder(userId);
    }

    // [상태만 수정]
    @Transactional
    public void updateOrderStatus(UUID id, OrderStatus newStatus, String userIdHeader) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        Long userId = Long.parseLong(userIdHeader);

        order.updateStatus(newStatus, userId);
    }
}
