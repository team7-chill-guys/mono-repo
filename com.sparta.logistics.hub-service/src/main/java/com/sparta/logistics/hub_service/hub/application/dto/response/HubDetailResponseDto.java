package com.sparta.logistics.hub_service.hub.application.dto.response;

import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HubDetailResponseDto {

  private UUID id;
  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;

  // 임시 허브 아이디
//  private Long fakeHubId;

  public static HubDetailResponseDto toResponse(Hub hub) {
    return new HubDetailResponseDto(
        hub.getId(),
        hub.getUserId(),
        hub.getHubName(),
        hub.getAddress(),
        hub.getLatitude(),
        hub.getLongitude()
    );
  }
}
