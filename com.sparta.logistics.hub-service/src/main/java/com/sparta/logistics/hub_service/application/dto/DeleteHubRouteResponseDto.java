package com.sparta.logistics.hub_service.application.dto;

import java.util.UUID;

public class DeleteHubRouteResponseDto {
  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;
}
