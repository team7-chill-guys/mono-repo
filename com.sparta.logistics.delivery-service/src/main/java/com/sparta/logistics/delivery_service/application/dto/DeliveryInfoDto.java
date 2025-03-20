package com.sparta.logistics.delivery_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryInfoDto {
    UUID departureHubId;
    UUID destinationHubId;
    Long deliveryManagerId;
    UUID orderId;
    String slackId;
}
