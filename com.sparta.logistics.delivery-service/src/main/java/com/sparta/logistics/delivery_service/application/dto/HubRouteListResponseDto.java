package com.sparta.logistics.delivery_service.application.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class HubRouteListResponseDto {
    private UUID startHubId;
    private UUID endHubId;
    private Integer deliveryTime;
    private Double deliveryDistance;
}
