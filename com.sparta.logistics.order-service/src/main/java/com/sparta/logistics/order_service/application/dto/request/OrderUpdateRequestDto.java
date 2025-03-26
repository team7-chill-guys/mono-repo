package com.sparta.logistics.order_service.application.dto.request;

import com.sparta.logistics.order_service.domain.OrderStatus;
import lombok.*;

@Getter
@Builder
public class OrderUpdateRequestDto {

    private OrderStatus status;
    private Long quantity;
    private String request;
}