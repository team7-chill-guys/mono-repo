package com.sparta.logistics.product_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestProductCreateDto {
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
}