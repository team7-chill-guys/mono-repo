package com.sparta.logistics.hub_service.hubroute.application.dto.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteCreateRequestDto {

  private BigDecimal startLatitude;
  private BigDecimal startLongitude;
  private BigDecimal endLatitude;
  private BigDecimal endLongitude;

  // TODO : 원래는 start_hub_id, end_hub_id 만 받아와야 한다.
}
