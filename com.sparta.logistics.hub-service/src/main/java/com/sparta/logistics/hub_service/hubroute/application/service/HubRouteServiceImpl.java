package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.domain.entity.HubRoute;
import com.sparta.logistics.hub_service.hubroute.domain.repository.HubRouteRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubRouteServiceImpl implements HubRouteService {

  private final HubRouteRepository hubRouteRepository;

  // 허브 루트 생성
  @Transactional
  @Override
  public HubRouteCreateResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {

    // 임시 UUID & userId생성
    UUID startHubId = UUID.randomUUID();
    UUID endHubId = UUID.randomUUID();
//    UUID startHubId = UUID.fromString("7c68bc02-c060-4660-9713-0e1e93192272");
//    UUID endHubId = UUID.fromString("a5705bcc-ec76-4a5c-8a84-da9f5a4d7ca5");

    String currentId = "1";

    if (hubRouteRepository.existsByStartHubId(startHubId) && hubRouteRepository.existsByEndHubId(
        endHubId)) {
      throw new IllegalArgumentException("이미 등록된 경로 입니다.");
    }

    HubRoute hubRoute = hubRouteRepository.save(
        HubRoute.builder()
//            .startHubId(requestDto.getStartHubId())
//            .endHubId(requestDto.getEndHubId())
            .startHubId(startHubId)
            .endHubId(endHubId)
            .startHubName(requestDto.getStartHubName())
            .endHubName(requestDto.getEndHubName())
            .deliveryTime(requestDto.getDeliveryTime())
            .deliveryDistance(requestDto.getDeliveryDistance())
            .createdBy(currentId)
            .updatedBy(currentId)
            .build()
    );

    return new HubRouteCreateResponseDto(hubRoute);
  }

  // 허브 루트 단일 조회
  @Override
  public HubRouteDetailResponseDto getHubRouteDetail(UUID hubRoutesId) {
    HubRoute hubRoute = hubRouteRepository.findById(hubRoutesId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 이동 경로가 없습니다."));
    return HubRouteDetailResponseDto.toResponse(hubRoute);
  }
}
