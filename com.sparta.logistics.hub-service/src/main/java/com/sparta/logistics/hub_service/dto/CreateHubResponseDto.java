package com.sparta.logistics.hub_service.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateHubResponseDto {

  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
