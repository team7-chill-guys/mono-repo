package com.sparta.logistics.hub_service.data;

import com.sparta.logistics.hub_service.dto.HubDetailResponseDto;
import com.sparta.logistics.hub_service.dto.HubListResponseDto;
import java.math.BigDecimal;
import java.util.List;

public class HubData {

  // 허브 단일 조회 임시 데이터
  public static final List<HubDetailResponseDto> hubDetailDatabase = List.of(
      HubDetailResponseDto.builder()
          .userId(1L)
          .hubId(1L)
          .hubName("서울특별시 센터")
          .address("서울특별시 송파구 송파대로55")
          .latitude(BigDecimal.valueOf(37.4742027808))
          .longitude(BigDecimal.valueOf(37.4742027808))
          .build()
  );

  // 허브 전체 조회 임시 데이터
  public static final List<HubListResponseDto> hubDatabase = List.of(
      HubListResponseDto.builder()
          .userId(1L)
          .hubName("서울특별시 센터")
          .address("서울특별시 송파구 송파대로55")
          .build(),
      HubListResponseDto.builder()
          .userId(1L)
          .hubName("경기 북부 센터")
          .address("경기도 고양시 덕양구 권율대로 570")
          .build()
  );

  // 허브 수정 임시 데이터
  public static final List<HubDetailResponseDto> hubUpdateDatabase = List.of(
      HubDetailResponseDto.builder()
          .userId(1L)
          .hubId(1L)
          .hubName("서울특별시 센터")
          .address("서울특별시 송파구 송파대로55")
          .latitude(BigDecimal.valueOf(37.4742027808))
          .longitude(BigDecimal.valueOf(37.4742027808))
          .build()
  );
  // 허브 삭제 임시 데이터
  public static final List<HubDetailResponseDto> hubDeleteDatabase = List.of(
      HubDetailResponseDto.builder()
          .userId(1L)
          .hubId(1L)
          .hubName("서울특별시 센터")
          .address("서울특별시 송파구 송파대로55")
          .latitude(BigDecimal.valueOf(37.4742027808))
          .longitude(BigDecimal.valueOf(37.4742027808))
          .build()
  );
}
