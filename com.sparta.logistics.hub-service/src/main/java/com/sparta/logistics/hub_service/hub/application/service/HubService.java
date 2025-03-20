package com.sparta.logistics.hub_service.hub.application.service;

import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.request.HubUpdateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubListResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubUpdateResponseDto;
import java.util.List;
import java.util.UUID;

public interface HubService {

  HubCreateResponseDto createHub(HubCreateRequestDto requestDto);

  HubDetailResponseDto getHubDetail(UUID hubId);

  List<HubListResponseDto> getHubList();

  List<HubListResponseDto> getSearchHubs(String hubName, String address, UUID hubId);

  HubUpdateResponseDto updateHub(UUID hubId, HubUpdateRequestDto requestDto);

  void deleteHub(Long userId, UUID hubId);
}
