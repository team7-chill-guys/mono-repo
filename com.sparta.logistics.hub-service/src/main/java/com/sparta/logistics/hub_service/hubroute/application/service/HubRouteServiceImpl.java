package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
  @Cacheable(
      value = "hubRoutes",
      key = "((#startHubId != null ? #startHubId.toString() : '')) + '-' + ((#endHubId != null ? #endHubId.toString() : ''))"
  )
  public List<HubRouteListResponseDto> getHubRouteList(UUID startHubId, UUID endHubId) {

    log.debug("DB 조회 시작: startHubId = {}, endHubId = {}", startHubId, endHubId);

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
  public HubRouteUpdateResponseDto updateHubRoute(UUID hubId, HubRouteUpdateRequestDto requestDto, String userIdHeader) {
    HubRoute hubRoute = hubRouteRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 이동 경로가 없습니다."));

    Long currentId = Long.valueOf(userIdHeader);

    hubRoute.updateUpdateBy(currentId);
    hubRoute.updateDeliveryTime(requestDto.getDeliveryTime());
    hubRoute.updateDeliveryDistance(requestDto.getDeliveryDistance());

    HubRoute updateHubRoute = hubRouteRepository.save(hubRoute);
    return new HubRouteUpdateResponseDto(updateHubRoute);
  }

  @Override
  @Transactional
  public void deleteHubRoute(Long userId, UUID hubRoutesId, String userIdHeader) {

    HubRoute hubRoute = hubRouteRepository.findById(hubRoutesId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 이동 경로 정보가 없습니다."));

    Long currentId = Long.valueOf(userIdHeader);

    hubRoute.setDeletedBy(currentId);
    hubRoute.setDeletedAt(LocalDateTime.now());
    hubRouteRepository.save(hubRoute);
    if (hubRoute.isDeleted()) {
      throw new IllegalStateException("이미 삭제된 허브 이동 경로입니다.");
    }

    hubRoute.softDelete(currentId);
  }
}
