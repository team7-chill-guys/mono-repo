package com.sparta.logistics.hub_service.hub.application.dto.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class HubCreateRequestDto {

  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
