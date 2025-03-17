package com.sparta.logistics.hub_service.hubroute.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteUpdateRequestDto {

  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;
}
