package com.sparta.logistics.hub_service.hubroute.application.service;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import com.sparta.logistics.hub_service.hubroute.domain.entity.HubRoute;
import com.sparta.logistics.hub_service.hubroute.domain.repository.HubRouteRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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

  // 허브 루트 전체 조회(목록) 및 검색
  @Override
  public List<HubRouteListResponseDto> getHubRouteList(String startHubName, String endHubName) {
    List<HubRoute> hubRoutes;

    if (isNotEmpty(startHubName) && isNotEmpty(endHubName)) {
      hubRoutes = hubRouteRepository.findByStartHubNameContainingAndEndHubNameContaining(startHubName,
          endHubName);
    } else if (isNotEmpty(startHubName) || isNotEmpty(endHubName)) {
      hubRoutes = hubRouteRepository.findByStartHubNameContainingOrEndHubNameContaining(startHubName,
          endHubName);
    } else {
      hubRoutes = hubRouteRepository.findAll();
    }
    return hubRoutes.stream()
        .map(HubRouteListResponseDto::toResponse)
        .collect(Collectors.toList());
  }

  // 허브 루트 수정
  @Override
  @Transactional
  public HubRouteUpdateResponseDto updateHubRoute(UUID hubId, HubRouteUpdateRequestDto requestDto) {
    HubRoute hubRoute = hubRouteRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 이동 경로가 없습니다."));

    // TODO : 추후 updateBy 값 -> 로그인한 userId 값 들어가게 변경
    hubRoute.updateDeliveryTime(requestDto.getDeliveryTime());
    hubRoute.updateDeliveryDistance(requestDto.getDeliveryDistance());

    HubRoute updateHubRoute = hubRouteRepository.save(hubRoute);
    return new HubRouteUpdateResponseDto(updateHubRoute);
  }

  public void deleteHubRoute(String userId, UUID hubRoutesId) {

    HubRoute hubRoute = hubRouteRepository.findById(hubRoutesId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    String currentId = "111"; // 임시 아이디

    hubRoute.setDeletedBy(currentId);
    hubRoute.setDeletedAt(LocalDateTime.now());
    hubRouteRepository.save(hubRoute);
  }
}
