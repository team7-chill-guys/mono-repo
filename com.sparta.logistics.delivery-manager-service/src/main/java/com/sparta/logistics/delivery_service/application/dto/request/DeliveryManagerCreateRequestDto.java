package com.sparta.logistics.delivery_service.application.dto.request;

import com.sparta.logistics.delivery_service.domain.model.DeliveryManagerType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryManagerCreateRequestDto {
    private UUID hubId;
    private String slackId;
    private DeliveryManagerType type;
}
