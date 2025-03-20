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

  // 허브 루트 단일 조회
  @Override
  public HubRouteDetailResponseDto getHubRouteDetail(UUID hubRoutesId) {
    HubRoute hubRoute = hubRouteRepository.findById(hubRoutesId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 이동 경로가 없습니다."));
    return HubRouteDetailResponseDto.toResponse(hubRoute);
  }

  // 허브 루트 전체 조회(목록) 및 검색
  @Override
  public List<HubRouteListResponseDto> getHubRouteList(UUID startHubId, UUID endHubId) {
    List<HubRoute> hubRoutes;

    if (startHubId != null && endHubId != null) {
      hubRoutes = hubRouteRepository.findByStartHubIdAndEndHubId(startHubId,
          endHubId);
    } else if (startHubId != null || endHubId != null) {
      hubRoutes = hubRouteRepository.findByStartHubIdOrEndHubId(startHubId,
          endHubId);
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

    // TODO : 허브 수정 -> 자동으로 허브 루트 수정 구현
    // TODO : 추후 updateBy 값 -> 로그인한 userId 값 들어가게 변경

    hubRoute.updateDeliveryTime(requestDto.getDeliveryTime());
    hubRoute.updateDeliveryDistance(requestDto.getDeliveryDistance());

    HubRoute updateHubRoute = hubRouteRepository.save(hubRoute);
    return new HubRouteUpdateResponseDto(updateHubRoute);
  }

  @Override
  public void deleteHubRoute(Long userId, UUID hubRoutesId) {

    HubRoute hubRoute = hubRouteRepository.findById(hubRoutesId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    // TODO : 허브 삭제 -> 관련 허브 루트 자동 삭제 구현
    // TODO : 추후 deleteBy 값 -> 로그인한 userId 값 들어가게 변경 & userId Long 타입으로 변경
    Long currentId = 1L; // 임시 아이디

    hubRoute.setDeletedBy(currentId);
    hubRoute.setDeletedAt(LocalDateTime.now());
    hubRouteRepository.save(hubRoute);
  }
}
