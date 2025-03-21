package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;

public interface KakaoMapService {
  HubRouteCreateResponseDto autoCreateHubRoute(HubRouteCreateRequestDto requestDto, String userIdHeader);

}
