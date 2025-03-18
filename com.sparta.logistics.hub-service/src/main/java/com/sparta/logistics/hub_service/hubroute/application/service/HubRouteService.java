package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import java.util.List;
import java.util.UUID;

public interface HubRouteService {

  HubRouteCreateResponseDto createHubRoute(HubRouteCreateRequestDto requestDto);

  HubRouteDetailResponseDto getHubRouteDetail(UUID hubRoutesId);

  List<HubRouteListResponseDto> getHubRouteList(String startHubName, String endHubName);

  HubRouteUpdateResponseDto updateHubRoute(UUID hubId, HubRouteUpdateRequestDto requestDto);

  void deleteHubRoute(String userId, UUID hubRoutesId);
}
