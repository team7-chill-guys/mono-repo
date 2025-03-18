package com.sparta.logistics.hub_service.hub.application.service;

import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import com.sparta.logistics.hub_service.hub.domain.repository.HubRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;

  // TODO : 마스터 관리자 경우에만 허브 생성 가능
  // 허브 생성
  @Transactional
  @Override
  public HubCreateResponseDto createHub(HubCreateRequestDto requestDto) {

    // 동일한 유저 아이디 존재
    if (hubRepository.existsByUserId(requestDto.getUserId())) {
      throw new IllegalArgumentException("이미 다른 허브에 관리자로 지정되어 있습니다");
    }
    // 동일한 허브 이름 존재
    if (hubRepository.existsByHubName(requestDto.getHubName())) {
      throw new IllegalArgumentException("이미 존재하는 허브 이름입니다.");
    }

    // 동일한 주소 존재
    if (hubRepository.existsByAddress(requestDto.getAddress())) {
      throw new IllegalArgumentException("이미 존재하는 주소입니다.");
    }

    // TODO : 추후 createBy, updateBy 값 -> 로그인한 userId 값 들어가게 변경
    // TODO : JPAAuditing 사용
    String currentId = "1";

    // 허브 엔티티 생성 및 저장
    Hub hub = hubRepository.save(
        Hub.builder()
            .userId(requestDto.getUserId())
            .hubName(requestDto.getHubName())
            .address(requestDto.getAddress())
            .latitude(requestDto.getLatitude())
            .longitude(requestDto.getLongitude())
            .createdBy(currentId)
            .updatedBy(currentId)
            .build()
    );

    log.info("허브 서비스 - builder 완료");
    return new HubCreateResponseDto(hub);
  }

  // 허브 단일 조회
  @Override
  public HubDetailResponseDto getHubDetail(UUID hubId) {
    log.info("여기는 허브 단일 조회 서비스 레이어");
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    return HubDetailResponseDto.toResponse(hub);
  }

}
