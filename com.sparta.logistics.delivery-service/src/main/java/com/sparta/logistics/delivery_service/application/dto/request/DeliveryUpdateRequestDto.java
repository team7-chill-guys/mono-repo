package com.sparta.logistics.delivery_service.application.dto.request;

import com.sparta.logistics.delivery_service.domain.model.DeliveryStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryUpdateRequestDto {
    private DeliveryStatus deliveryStatus;
    private UUID departureHubId;
    private UUID destinationHubId;
    private String address;
    private String slackId;
    private String phoneNumber;
    private UUID deliveryManagerId;
}
