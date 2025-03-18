package com.sparta.logistics.order_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderListResponseDto {
    private List<OrderDetailResponseDto> orders;
}
