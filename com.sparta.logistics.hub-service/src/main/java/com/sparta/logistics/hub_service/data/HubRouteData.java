package com.sparta.logistics.hub_service.data;

import com.sparta.logistics.hub_service.application.dto.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.application.dto.HubRouteListResponseDto;
import java.util.List;
import java.util.UUID;

public class HubRouteData {

  static UUID startHubId = UUID.randomUUID(); // 자동 생성
  static UUID endHubId = UUID.randomUUID(); // 자동 생성

  // 허브 루트 단일 조회 임시 데이터
  public static final List<HubRouteDetailResponseDto> hubRouteDetailDatabase = List.of(
      HubRouteDetailResponseDto.builder()
          .id(1L)
          .startHubId(startHubId)
          .endHubId(endHubId)
          .startHubName("서울특별시 센터")
          .endHubName("부산광역시 센터")
          .deliveryTime(350L)
          .deliveryDistance(400L)
          .build()
  );
  // 허브 루트 전체 조회 임시 데이터
  public static final List<HubRouteListResponseDto> hubRouteDatabase = List.of(
      HubRouteListResponseDto.builder()
          .startHubName("서울특별시 센터")
          .endHubName("부산광역시 센터")
          .deliveryTime(350L)
          .deliveryDistance(400L)
          .build(),
      HubRouteListResponseDto.builder()
          .startHubName("서울특별시 센터")
          .endHubName("인천광역시 센터")
          .deliveryTime(350L)
          .deliveryDistance(400L)
          .build()
  );
  // 허브 루트 수정 임시 데이터
  public static final List<HubRouteDetailResponseDto> hubRouteUpdateDatabase = List.of(
      HubRouteDetailResponseDto.builder()
          .id(1L)
          .startHubId(startHubId)
          .endHubId(endHubId)
          .startHubName("서울특별시 센터")
          .endHubName("부산광역시 센터")
          .deliveryTime(350L)
          .deliveryDistance(400L)
          .build()
  );

  // 허브 루트 삭제 임시 데이터
  public static final List<HubRouteDetailResponseDto> hubRouteDeleteDatabase = List.of(
      HubRouteDetailResponseDto.builder()
          .id(1L)
          .startHubId(startHubId)
          .endHubId(endHubId)
          .startHubName("서울특별시 센터")
          .endHubName("부산광역시 센터")
          .deliveryTime(350L)
          .deliveryDistance(400L)
          .build()
  );

}
