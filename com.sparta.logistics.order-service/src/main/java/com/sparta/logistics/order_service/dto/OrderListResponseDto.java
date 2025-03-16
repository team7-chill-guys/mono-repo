package com.sparta.logistics.order_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderListResponseDto {

    private List<OrderDetailResponseDto> orders;
    private int totalCount;
}
