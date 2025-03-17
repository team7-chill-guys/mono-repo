package com.sparta.logistics.product_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestProductUpdateDto {
    private String name;
    private Long stock;
}
