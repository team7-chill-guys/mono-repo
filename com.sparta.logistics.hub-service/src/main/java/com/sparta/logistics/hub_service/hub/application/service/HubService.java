package com.sparta.logistics.hub_service.hub.application.service;

import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubListResponseDto;
import java.util.List;
import java.util.UUID;

public interface HubService {

  HubCreateResponseDto createHub(HubCreateRequestDto requestDto);

  HubDetailResponseDto getHubDetail(UUID hubId);

  List<HubListResponseDto> getHubList();
}
