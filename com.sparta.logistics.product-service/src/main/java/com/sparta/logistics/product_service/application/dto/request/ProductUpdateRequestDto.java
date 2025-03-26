package com.sparta.logistics.product_service.application.dto.request;

import lombok.*;

@Getter
@Builder
public class ProductUpdateRequestDto {
    private String name;
    private Long stock;
}
