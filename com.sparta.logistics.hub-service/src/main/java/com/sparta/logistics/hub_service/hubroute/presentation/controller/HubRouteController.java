package com.sparta.logistics.hub_service.hubroute.presentation.controller;

import com.sparta.logistics.hub_service.global.dto.ResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.service.HubRouteService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hub-routes")
@RequiredArgsConstructor
public class HubRouteController {

  private final HubRouteService hubRouteService;

  // TODO : 마스터 관리자 경우에만 허브 생성, 수정, 삭제 가능

  // 허브 루트 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubRouteCreateResponseDto>> createHubRoute(
      @RequestBody HubRouteCreateRequestDto requestDto) {
    HubRouteCreateResponseDto responseDto = hubRouteService.createHubRoute(requestDto);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 단일 조회
  @GetMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteDetailResponseDto>> getHubRouteDetail(
      @PathVariable UUID hubRoutesId) {
    HubRouteDetailResponseDto responseDto = hubRouteService.getHubRouteDetail(hubRoutesId);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 조회 및 검색
  @GetMapping
  public ResponseEntity<ResponseDto<List<HubRouteListResponseDto>>> getHubRouteList(
      @RequestParam(required = false) String startHubName,
      @RequestParam(required = false) String endHubName) {
    List<HubRouteListResponseDto> responseDto = hubRouteService.getHubRouteList(startHubName,
        endHubName);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 수정
  @PutMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteUpdateResponseDto>> updateHubRoute(
      @PathVariable UUID hubRoutesId,
      @RequestBody HubRouteUpdateRequestDto requestDto) {
    HubRouteUpdateResponseDto responseDto = hubRouteService.updateHubRoute(hubRoutesId, requestDto);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 삭제
  @DeleteMapping("/{hubRoutesId}")
  public ResponseEntity<?> deleteHubRoute(Long userId, @PathVariable UUID hubRoutesId) {
    hubRouteService.deleteHubRoute(userId, hubRoutesId);
    return ResponseEntity.ok(ResponseDto.success("delete success"));
  }
}
