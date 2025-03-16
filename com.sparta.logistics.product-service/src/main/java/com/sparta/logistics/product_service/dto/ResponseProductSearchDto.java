package com.sparta.logistics.product_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseProductSearchDto {
    private List<ResponseProductGetDto> products;
    private int page;
    private int size;
}
