package com.sparta.logistics.product_service.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockResponseDto {
    private UUID productId;
    private Long stock;
}
