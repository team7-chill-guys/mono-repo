package com.sparta.logistics.delivery_service.application.dto.mock;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderInfoDto {
    private UUID orderId;
    private UUID supplierId;
    private UUID receiverId;
    private UUID productId;
}
