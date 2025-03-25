package com.sparta.logistics.hub_service.hubroute.presentation.controller;

import com.sparta.logistics.hub_service.global.dto.ResponseDto;
import com.sparta.logistics.hub_service.global.utils.JwtUtil;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.service.HubRouteService;
import com.sparta.logistics.hub_service.hubroute.application.service.KakaoMapApiServiceImpl;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master/hub-routes")
@RequiredArgsConstructor
@Slf4j
public class MasterHubRouteController {

  private final JwtUtil jwtUtil;
  private final KakaoMapApiServiceImpl kakaoMapApiService;
  private final HubRouteService hubRouteService;

  // 허브 루트 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubRouteCreateResponseDto>> createHubRoute(
      @RequestBody HubRouteCreateRequestDto requestDto,
      @RequestHeader("Authorization") String authHeader) {
    String userIdHeader = jwtUtil.extractUserIdFromAuthHeader(authHeader);
    HubRouteCreateResponseDto responseDto = kakaoMapApiService.createHubRoute(requestDto,
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 자동 생성
  @PostMapping("/auto")
  public ResponseEntity<ResponseDto<List<HubRouteCreateResponseDto>>> autoCreateHubRoute(
      @RequestHeader("Authorization") String authHeader) {
    String userIdHeader = jwtUtil.extractUserIdFromAuthHeader(authHeader);
    List<HubRouteCreateResponseDto> responseDto = kakaoMapApiService.autoCreateHubRoute(
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 수정
  @PutMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteUpdateResponseDto>> updateHubRoute(
      @PathVariable UUID hubRoutesId,
      @RequestBody HubRouteUpdateRequestDto requestDto,
      @RequestHeader("Authorization") String authHeader) {
    String userIdHeader = jwtUtil.extractUserIdFromAuthHeader(authHeader);
    HubRouteUpdateResponseDto responseDto = hubRouteService.updateHubRoute(hubRoutesId, requestDto,
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 삭제
  @DeleteMapping("/{hubRoutesId}")
  public ResponseEntity<?> deleteHubRoute(Long userId, @PathVariable UUID hubRoutesId,
      @RequestHeader("Authorization") String authHeader) {
    String userIdHeader = jwtUtil.extractUserIdFromAuthHeader(authHeader);
    hubRouteService.deleteHubRoute(userId, hubRoutesId, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success("delete success"));
  }
}
