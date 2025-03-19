package com.sparta.logistics.order_service.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderDeleteResponseDto {

    private UUID orderId;
    private Timestamp deletedAt;
    private Long deletedBy;
}

