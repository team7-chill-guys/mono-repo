package com.sparta.logistics.order_service.controller;

import com.sparta.logistics.order_service.dto.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final Map<UUID, OrderDetailResponseDto> orderDatabase = new HashMap<>();

    // [등록]
    @PostMapping
    public OrderDetailResponseDto createOrder(@RequestBody OrderCreateRequestDto requestDto) {
        UUID orderId = UUID.randomUUID();
        OrderDetailResponseDto orderResponse = OrderDetailResponseDto.builder()
                .orderId(orderId)
                .requestCompanyId(requestDto.getRequestCompanyId())
                .responseCompanyId(requestDto.getResponseCompanyId())
                .deliveryId(requestDto.getDeliveryId())
                .status(requestDto.getStatus())
                .quantity(requestDto.getQuantity())
                .request(requestDto.getRequest())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .createdBy(requestDto.getCreatedBy())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .updatedBy(requestDto.getUpdatedBy())
                .deletedAt(null)
                .deletedBy(null)
                .build();

        orderDatabase.put(orderId, orderResponse);
        return orderResponse;
    }

    // [수정]
    @PutMapping("/{id}")
    public OrderDetailResponseDto updateOrder(@PathVariable UUID id, @RequestBody OrderUpdateRequestDto updateDto) {
        OrderDetailResponseDto existingOrder = orderDatabase.get(id);

        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }

        if (existingOrder.getDeletedAt() != null) {
            throw new RuntimeException("This order has been deleted and cannot be modified.");
        }

        existingOrder.setStatus(updateDto.getStatus());
        existingOrder.setQuantity(updateDto.getQuantity());
        existingOrder.setRequest(updateDto.getRequest());
        existingOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        existingOrder.setUpdatedBy(updateDto.getUpdatedBy());

        return existingOrder;
    }

    // [삭제]
    @DeleteMapping("/{id}")
    public OrderDeleteResponseDto deleteOrder(@PathVariable UUID id) {
        OrderDetailResponseDto order = orderDatabase.get(id);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (order.getDeletedAt() != null) {
            throw new RuntimeException("This order has already been deleted.");
        }

        order.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        order.setDeletedBy(order.getUpdatedBy());

        return new OrderDeleteResponseDto(order.getOrderId(), order.getDeletedAt(), order.getDeletedBy());
    }

    // [개별 조회]
    @GetMapping("/{id}")
    public OrderDetailResponseDto getOrderById(@PathVariable UUID id) {
        OrderDetailResponseDto order = orderDatabase.get(id);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (order.getDeletedAt() != null) {
            throw new RuntimeException("This order has already been deleted.");
        }

        return order;
    }

    // [전체 조회]
    @GetMapping
    public List<OrderDetailResponseDto> getAllOrders() {
        return orderDatabase.values().stream()
                .filter(order -> order.getDeletedAt() == null)
                .toList();
    }
}
