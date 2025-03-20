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
    private String slackId;
    private String phone;
    private String address;
    private OrderStatus status;
    private long quantity;
    private String request;
    private Long createdBy;
}
