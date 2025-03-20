package com.sparta.logistics.delivery_service.application.dto.response;

import com.sparta.logistics.delivery_service.domain.model.DeliveryManagerType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DeliveryManagerResponseDto {
    private UUID hubId;
    private String slackId;
    private DeliveryManagerType type;
    private Long sequence;
}
