package com.sparta.logistics.hub_service.hubroute.application.dto.response;

import com.sparta.logistics.hub_service.hubroute.domain.entity.HubRoute;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRouteCreateResponseDto {

  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;

  public HubRouteCreateResponseDto(HubRoute hubRoute) {
    this.startHubId = hubRoute.getStartHubId();
    this.endHubId = hubRoute.getEndHubId();
    this.startHubName = hubRoute.getStartHubName();
    this.endHubName = hubRoute.getEndHubName();
    this.deliveryTime = hubRoute.getDeliveryTime();
    this.deliveryDistance = hubRoute.getDeliveryDistance();
  }
}
