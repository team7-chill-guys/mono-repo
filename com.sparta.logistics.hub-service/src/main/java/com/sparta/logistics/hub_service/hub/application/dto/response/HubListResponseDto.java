package com.sparta.logistics.hub_service.hub.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubListResponseDto {

  private long userId;
  private String hubName;
  private String address;
}
