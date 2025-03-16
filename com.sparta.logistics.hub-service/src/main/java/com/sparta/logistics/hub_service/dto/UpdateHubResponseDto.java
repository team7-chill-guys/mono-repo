package com.sparta.logistics.hub_service.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateHubResponseDto {
  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;

  // 테스트를 위한 임시 추가
  private long hubId;
}
