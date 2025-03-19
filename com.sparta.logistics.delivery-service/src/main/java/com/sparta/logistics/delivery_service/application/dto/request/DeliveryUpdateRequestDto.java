package com.sparta.logistics.delivery_service.application.dto.request;

import com.sparta.logistics.delivery_service.domain.model.DeliveryStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryUpdateRequestDto {
    private UUID departureHubId;
    private UUID destinationHubId;
    private UUID companyId;
    private String address;
    private String slackId;
    private String phoneNumber;
    private Long deliveryManagerId;
}
