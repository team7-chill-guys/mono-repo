package com.sparta.logistics.order_service.service;

import com.sparta.logistics.order_service.domain.Order;
import com.sparta.logistics.order_service.domain.OrderStatus;
import com.sparta.logistics.order_service.dto.request.OrderCreateRequestDto;
import com.sparta.logistics.order_service.dto.request.OrderUpdateRequestDto;
import com.sparta.logistics.order_service.dto.response.OrderDetailResponseDto;
import com.sparta.logistics.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // [주문 생성]
    @Transactional
    public OrderDetailResponseDto createOrder(OrderCreateRequestDto requestDto) {
        Order order = orderRepository.save(requestDto.toEntity());
        return OrderDetailResponseDto.fromEntity(order);
    }


    // [조회]
    @Transactional(readOnly = true)
    public List<OrderDetailResponseDto> getAllOrders() {
        return orderRepository.findAllByDeletedAtIsNull().stream()
                .map(OrderDetailResponseDto::fromEntity)
                .toList();
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
    public OrderDetailResponseDto updateOrder(UUID id, OrderUpdateRequestDto updateDto) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        order.updateOrder(updateDto.getStatus(), updateDto.getQuantity(), updateDto.getRequest(), updateDto.getUpdatedBy());

        return OrderDetailResponseDto.fromEntity(order);
    }

    // [삭제]
    @Transactional
    public void deleteOrder(UUID id, Long deletedBy) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        order.deleteOrder(deletedBy);
    }

    // [상태만 수정]
    @Transactional
    public void updateOrderStatus(UUID id, OrderStatus newStatus) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        order.updateStatus(newStatus);
    }
}
