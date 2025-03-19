package com.sparta.logistics.hub_service.hubroute.application.dto.response;

import com.sparta.logistics.hub_service.hubroute.domain.entity.HubRoute;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteListResponseDto {

  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Integer deliveryTime;
  private Double deliveryDistance;

  public static HubRouteListResponseDto toResponse(HubRoute hubRoute) {
    return new HubRouteListResponseDto(
        hubRoute.getStartHubId(),
        hubRoute.getEndHubId(),
        hubRoute.getStartHubName(),
        hubRoute.getEndHubName(),
        hubRoute.getDeliveryTime(),
        hubRoute.getDeliveryDistance()
    );
  }
}
