package com.sparta.logistics.order_service.presentation.controller;

import com.sparta.logistics.order_service.domain.OrderStatus;
import com.sparta.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sparta.logistics.order_service.application.dto.request.OrderUpdateRequestDto;
import com.sparta.logistics.order_service.application.dto.response.OrderDetailResponseDto;
import com.sparta.logistics.order_service.application.dto.response.PageResponseDto;
import com.sparta.logistics.order_service.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // [등록]
    @PostMapping
    public ResponseEntity<OrderDetailResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto) {
        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

    // [전체 조회]
    @GetMapping
    public ResponseEntity<PageResponseDto<OrderDetailResponseDto>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    // [개별 조회]
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // [수정]
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDto> updateOrder(@PathVariable UUID id, @RequestBody OrderUpdateRequestDto updateDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, updateDto));
    }

    // [삭제]
    // deletedby 는 유저에서 값 받아오는걸로 수정 예정
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID id, @RequestParam Long deletedBy) {
        orderService.deleteOrder(id, deletedBy);
        return ResponseEntity.ok("Order deleted successfully");
    }

    // [상태 수정]
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam OrderStatus status
    ) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok("Order status updated successfully");
    }
}
