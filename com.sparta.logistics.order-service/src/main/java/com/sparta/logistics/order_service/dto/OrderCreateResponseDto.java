package com.sparta.logistics.order_service.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class OrderCreateResponseDto {
    private UUID id;
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
