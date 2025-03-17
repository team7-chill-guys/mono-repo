package com.sparta.logistics.product_service.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequestDto {
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
}