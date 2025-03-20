package com.sparta.logistics.product_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockRequestDto {
    private UUID productId;
    private Long quantity;
}
