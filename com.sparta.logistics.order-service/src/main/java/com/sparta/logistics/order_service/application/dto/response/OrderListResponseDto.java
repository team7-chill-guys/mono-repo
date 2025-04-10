package com.sparta.logistics.order_service.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderListResponseDto {
    private List<OrderDetailResponseDto> orders;
}
