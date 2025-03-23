package com.sparta.logistics.hub_service.hub.application.service;

import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.request.HubUpdateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubListResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubUpdateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.UserRoleSearchResponseDto;
import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import com.sparta.logistics.hub_service.hub.domain.repository.HubRepository;
import com.sparta.logistics.hub_service.hub.infrastructure.Client.UserClient;
import com.sparta.logistics.hub_service.hubroute.application.service.KakaoMapApiServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;
  private final KakaoMapApiServiceImpl kakaoMapApiService;
  private final UserClient userClient;

  // 허브 생성
  @Transactional
  @Override
  public HubCreateResponseDto createHub(HubCreateRequestDto requestDto, String userIdHeader) {

    List<UserRoleSearchResponseDto> userRoles = userClient.roleSearchUser("ROLE_HUB_MANAGER");
    if (userRoles != null && !userRoles.isEmpty()) {
      log.info("허브 매니저 권한을 가진 유저 목록:");
      for (UserRoleSearchResponseDto user : userRoles) {
        log.info(" - 유저 ID: {}, 이름: {}, 권한: {}",
            user.getUserId(),
            user.getUsername(),
            user.getRole());
      }
    } else {
      log.info("ROLE_HUB_MANAGER 권한을 가진 유저가 없습니다.");
    }
    log.info("받아온 유저 정보 : " + userRoles);

//    if (hubRepository.existsByUserId(requestDto.getUserId())) {
//      throw new IllegalArgumentException("이미 다른 허브에 관리자로 지정되어 있습니다");
//    }
    if (hubRepository.existsByHubName(requestDto.getHubName())) {
      throw new IllegalArgumentException("이미 존재하는 허브 이름입니다.");
    }
    if (hubRepository.existsByAddress(requestDto.getAddress())) {
      throw new IllegalArgumentException("이미 존재하는 주소입니다.");
    }

    Long currentId = Long.valueOf(userIdHeader);

    Hub hub = hubRepository.save(
        Hub.builder()
            .userId(currentId)
            .hubName(requestDto.getHubName())
            .address(requestDto.getAddress())
            .latitude(requestDto.getLatitude())
            .longitude(requestDto.getLongitude())
            .createdBy(currentId)
            .updatedBy(currentId)
            .build()
    );

// TODO: 허브 생성 시, 모든 가능한 이동 경로를 자동으로 생성 (반복문을 사용하여 허브들 간의 가능한 모든 경로 조합을 계산하여 생성)
// kakaoMapApiService.autoCreateHubRoute();

    return new HubCreateResponseDto(hub);
  }

  // 허브 단일 조회
  @Override
  public HubDetailResponseDto getHubDetail(UUID hubId) {
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));
    return HubDetailResponseDto.toResponse(hub);
  }

  // 허브 전체 조회
  @Override
  public Page<HubListResponseDto> getHubList(Pageable pageable) {
    List<Hub> hubs = hubRepository.findAll();
    List<HubListResponseDto> dtoList = hubs.stream()
        .map(HubListResponseDto::toResponse)
        .collect(Collectors.toList());
    return paginateList(dtoList, pageable);
  }

  // 허브 검색 서비스
  @Override
  public Page<HubListResponseDto> getSearchHubs(String hubName, String address, UUID hubId,
      Pageable pageable) {
    if (hubId != null) {
      List<Hub> hubs = new ArrayList<>();
      hubRepository.findById(hubId).ifPresent(hubs::add);
      List<HubListResponseDto> dtoList = hubs.stream()
          .map(HubListResponseDto::toResponse)
          .collect(Collectors.toList());

      return paginateList(dtoList, pageable);
    } else {
      Page<Hub> hubPage = hubRepository.findByHubNameContainingOrAddressContaining(hubName, address,
          pageable);
      return hubPage.map(HubListResponseDto::toResponse);
    }

  }

  private <T> Page<T> paginateList(List<T> list, Pageable pageable) {
    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), list.size());
    List<T> subList = start > list.size() ? Collections.emptyList() : list.subList(start, end);
    return new PageImpl<>(subList, pageable, list.size());
  }


  // 허브 수정
  @Override
  @Transactional
  public HubUpdateResponseDto updateHub(UUID hubId, HubUpdateRequestDto requestDto,
      String userIdHeader) {
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    if (!Objects.equals(requestDto.getUserId(), hub.getUserId())) {
      if (hubRepository.existsByUserId(requestDto.getUserId())) {
        throw new IllegalArgumentException("이미 다른 허브에 관리자로 지정되어 있습니다");
      }
      hub.updateUserId(requestDto.getUserId());
    }

    if (!Objects.equals(requestDto.getHubName(), hub.getHubName())) {
      if (hubRepository.existsByHubName(requestDto.getHubName())) {
        throw new IllegalArgumentException("이미 존재하는 허브 이름입니다.");
      }
      hub.updateHubName(requestDto.getHubName());
    }

    if (!Objects.equals(requestDto.getAddress(), hub.getAddress())) {
      if (hubRepository.existsByAddress(requestDto.getAddress())) {
        throw new IllegalArgumentException("이미 존재하는 주소입니다.");
      }
      hub.updateAddress(requestDto.getAddress());
    }
    Long currentId = Long.valueOf(userIdHeader);
    hub.updateUpdateBy(currentId);
    hub.updateLatitude(requestDto.getLatitude());
    hub.updateLongitude(requestDto.getLongitude());

    Hub updateHub = hubRepository.save(hub);
    return new HubUpdateResponseDto(updateHub);
  }

  public void deleteHub(Long userId, UUID hubId, String userIdHeader) {

    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    Long currentId = Long.valueOf(userIdHeader);
    hub.setDeletedBy(currentId);
    hub.setDeletedAt(LocalDateTime.now());
    hubRepository.save(hub);
  }
}
