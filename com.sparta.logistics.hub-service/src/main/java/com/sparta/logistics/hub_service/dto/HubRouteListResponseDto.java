package com.sparta.logistics.hub_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HubRouteListResponseDto {
  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;
}
