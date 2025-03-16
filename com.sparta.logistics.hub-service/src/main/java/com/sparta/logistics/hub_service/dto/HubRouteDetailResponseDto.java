package com.sparta.logistics.hub_service.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class HubRouteDetailResponseDto {

  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Long deliveryTime;
  private Long deliveryDistance;

  private Long id; // 임시

}
