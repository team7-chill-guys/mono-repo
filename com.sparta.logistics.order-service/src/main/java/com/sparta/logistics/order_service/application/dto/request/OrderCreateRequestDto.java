package com.sparta.logistics.order_service.application.dto.request;

import com.sparta.logistics.order_service.domain.Order;
import com.sparta.logistics.order_service.domain.OrderStatus;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class OrderCreateRequestDto {

    private UUID productId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private OrderStatus status;
    private long quantity;
    private String request;
    private long createdBy;

    public Order toEntity(UUID deliveryId) {
        return Order.builder()
                .orderId(UUID.randomUUID())
                .deliveryId(deliveryId)
                .productId(productId)
                .requestCompanyId(requestCompanyId)
                .responseCompanyId(responseCompanyId)
                .status(status)
                .quantity(quantity)
                .request(request)
                .createdBy(createdBy)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedBy(createdBy)
                .updatedAt(Timestamp.from(Instant.now()))
                .deletedBy(null)
                .deletedAt(null)
                .build();
    }
}