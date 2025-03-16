package com.sparta.logistics.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderDetailResponseDto {

    private UUID orderId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private UUID deliveryId;
    private String status;
    private long quantity;
    private String request;
    private Timestamp createdAt;
    private long createdBy;
    private Timestamp updatedAt;
    private long updatedBy;
    private Timestamp deletedAt;
    private Long deletedBy;
}
