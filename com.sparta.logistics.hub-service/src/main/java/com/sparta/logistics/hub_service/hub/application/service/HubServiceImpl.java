package com.sparta.logistics.hub_service.hub.application.service;

import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import com.sparta.logistics.hub_service.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;

  // 허브 생성
  @Transactional
  public HubCreateResponseDto createHub(HubCreateRequestDto requestDto) {

    // TODO : 마스터 관리자 경우에만 허브 생성 가능

    // 허브 엔티티 생성 및 저장
    Hub hub = hubRepository.save(
        Hub.builder()
            .userId(requestDto.getUserId()) // User ID
            .hubName(requestDto.getHubName()) // 허브 이름
            .address(requestDto.getAddress()) // 주소
            .latitude(requestDto.getLatitude()) // 위도
            .longitude(requestDto.getLongitude()) // 경도
            .build()
    );

    log.info("허브 서비스 - builder 완료");
    return new HubCreateResponseDto(hub);
  }
}
