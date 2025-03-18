package com.sparta.logistics.delivery_service.application.dto.mock;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class HubRouteInfoDto {
    private UUID hubRouteId;
    private UUID departureHubId;
    private UUID destinationHubId;
    private Integer estimatedTime;
    private Double estimatedDistance;
}
