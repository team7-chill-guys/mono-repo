package com.sparta.logistics.product_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class ProductStockRequestDto {
    private UUID productId;
    private Long quantity;
}
