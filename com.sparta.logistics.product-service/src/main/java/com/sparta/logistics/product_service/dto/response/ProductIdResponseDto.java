package com.sparta.logistics.product_service.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductIdResponseDto {
    private UUID productId;
}
