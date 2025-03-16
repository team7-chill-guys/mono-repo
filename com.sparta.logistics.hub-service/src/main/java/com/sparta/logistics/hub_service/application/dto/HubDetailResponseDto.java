package com.sparta.logistics.hub_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HubDetailResponseDto {

  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;

  // 테스트를 위한 임시 추가
  private long hubId;


}
