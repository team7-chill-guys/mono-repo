package com.sparta.logistics.hub_service.application.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class HubRouteCreateRequestDto {

  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;
}
