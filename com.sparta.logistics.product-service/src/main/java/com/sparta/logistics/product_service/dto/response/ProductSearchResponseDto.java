package com.sparta.logistics.product_service.dto.response;

import com.sparta.logistics.product_service.dto.response.ProductGetResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchResponseDto {
    private List<ProductGetResponseDto> products;
    private int page;
    private int size;
}
