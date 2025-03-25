package com.sparta.logistics.slack_service.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderDetailResponseDto {
    private UUID orderId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private String slackId;
    private String phone;
    private String address;
    private UUID deliveryId;
    private Long quantity;
    private String status;
    private String request;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Long deletedBy;
    private Timestamp deletedAt;
}
