package com.sparta.logistics.delivery_service.application.dto.mock;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductInfoDto {
    private UUID productId;
    private UUID companyId;
    private UUID hubId;
}
