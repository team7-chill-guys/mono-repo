package com.sparta.logistics.order_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class OrderUpdateRequestDto {

    private String status;
    private long quantity;
    private String request;
    private long updatedBy;
}
