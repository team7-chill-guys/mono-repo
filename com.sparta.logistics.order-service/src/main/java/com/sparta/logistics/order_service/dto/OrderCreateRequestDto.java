package com.sparta.logistics.order_service.dto;

import lombok.*;
import java.util.UUID;

@Setter
@Getter
@Builder
public class OrderCreateRequestDto {

    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private UUID deliveryId;
    private String status;
    private long quantity;
    private String request;
    private long createdBy;
    private long updatedBy;
}
