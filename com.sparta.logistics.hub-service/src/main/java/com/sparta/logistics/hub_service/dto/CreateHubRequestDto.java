package com.sparta.logistics.hub_service.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateHubRequestDto {

  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
