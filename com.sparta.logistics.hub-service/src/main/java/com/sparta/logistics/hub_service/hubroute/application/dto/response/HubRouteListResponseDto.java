package com.sparta.logistics.hub_service.hubroute.application.dto.response;

import com.sparta.logistics.hub_service.hubroute.domain.entity.HubRoute;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteListResponseDto {

  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;

  public static HubRouteListResponseDto toResponse(HubRoute hubRoute) {
    return new HubRouteListResponseDto(
        hubRoute.getStartHubName(),
        hubRoute.getEndHubName(),
        hubRoute.getDeliveryTime(),
        hubRoute.getDeliveryDistance()
    );
  }
}
