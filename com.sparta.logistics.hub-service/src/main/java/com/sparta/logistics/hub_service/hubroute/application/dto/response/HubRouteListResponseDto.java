package com.sparta.logistics.hub_service.hubroute.application.dto.response;

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
