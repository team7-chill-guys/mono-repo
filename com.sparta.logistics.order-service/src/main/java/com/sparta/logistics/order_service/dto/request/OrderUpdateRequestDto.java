package com.sparta.logistics.order_service.dto.request;

import com.sparta.logistics.order_service.domain.OrderStatus;
import lombok.*;
import java.util.UUID;

@Getter
@Builder
public class OrderUpdateRequestDto {

    private OrderStatus status;
    private long quantity;
    private String request;
    private long updatedBy;
}