package com.sparta.logistics.order_service.application.dto.request;

import com.sparta.logistics.order_service.domain.OrderStatus;
import lombok.*;
import java.util.UUID;

@Getter
@Builder
public class OrderUpdateRequestDto {

    private OrderStatus status;
    private long quantity;
    private String request;
    private Long updatedBy;
}