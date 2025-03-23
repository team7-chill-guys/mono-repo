package com.sparta.logistics.hub_service.hubroute.presentation.controller;

import com.sparta.logistics.hub_service.global.dto.ResponseDto;
import com.sparta.logistics.hub_service.global.utils.PageableUtils;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.service.HubRouteService;
import com.sparta.logistics.hub_service.hubroute.application.service.KakaoMapApiServiceImpl;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hub-routes")
@RequiredArgsConstructor
@Slf4j
public class HubRouteController {

  private final HubRouteService hubRouteService;
  private final KakaoMapApiServiceImpl kakaoMapApiService;

  // TODO : 마스터 관리자 경우에만 허브 생성, 수정, 삭제 가능

  // 허브 루트 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubRouteCreateResponseDto>> createHubRoute(
      @RequestBody HubRouteCreateRequestDto requestDto,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    HubRouteCreateResponseDto responseDto = kakaoMapApiService.autoCreateHubRoute(requestDto,
        userIdHeader);

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
  // URL: /api/hub-routes
  @GetMapping
  public ResponseEntity<List<HubRouteListResponseDto>> getHubRouteList(
      @RequestParam(required = false) UUID startHubId,
      @RequestParam(required = false) UUID endHubId,
      @PageableDefault(
          size = 10,
          page = 1,
          sort = {"createdAt", "updatedAt"},
          direction = Direction.ASC
      ) Pageable pageable) {

    Pageable validatedPageable = PageableUtils.validatePageable(pageable);


    Page<HubRouteListResponseDto> responseDto = hubRouteService.getHubRouteList(startHubId,
        endHubId, validatedPageable);
    List<HubRouteListResponseDto> listResponseDto = responseDto.getContent();
    return ResponseEntity.ok().body(listResponseDto);
  }

  // 허브 루트 수정
  @PutMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteUpdateResponseDto>> updateHubRoute(
      @PathVariable UUID hubRoutesId,
      @RequestBody HubRouteUpdateRequestDto requestDto,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    HubRouteUpdateResponseDto responseDto = hubRouteService.updateHubRoute(hubRoutesId, requestDto,
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 삭제
  @DeleteMapping("/{hubRoutesId}")
  public ResponseEntity<?> deleteHubRoute(Long userId, @PathVariable UUID hubRoutesId,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    hubRouteService.deleteHubRoute(userId, hubRoutesId, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success("delete success"));
  }
}
