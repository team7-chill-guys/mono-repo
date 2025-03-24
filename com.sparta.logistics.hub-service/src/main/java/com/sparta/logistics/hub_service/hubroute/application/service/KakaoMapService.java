package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import java.util.List;

public interface KakaoMapService {

  HubRouteCreateResponseDto createHubRoute(HubRouteCreateRequestDto requestDto,
      String userIdHeader);

  List<HubRouteCreateResponseDto> autoCreateHubRoute(String userIdHeader);

}
