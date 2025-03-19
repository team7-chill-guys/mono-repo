package com.sparta.logistics.delivery_service.application.service.mock;

import com.sparta.logistics.delivery_service.application.dto.mock.OrderInfoDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockOrderService {

    UUID orderId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
    UUID productId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
    UUID receiverId = UUID.fromString("123e4567-e89b-12d3-a456-42661417409");

    public OrderInfoDto getOrderInfoDto() {
        return OrderInfoDto.builder()
                .orderId(orderId)
                .supplierId(UUID.randomUUID())
                .receiverId(receiverId)
                .productId(productId)
                .build();
    }

    public UUID getProductId(UUID orderId) {
        return getOrderInfoDto().getProductId();
    }
    public UUID getReceiverId(UUID orderId) {
        return getOrderInfoDto().getReceiverId();
    }
}
