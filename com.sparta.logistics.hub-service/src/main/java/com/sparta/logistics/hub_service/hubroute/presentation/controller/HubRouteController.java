package com.sparta.logistics.hub_service.hubroute.presentation.controller;

import com.sparta.logistics.hub_service.global.dto.ResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.service.HubRouteService;
import com.sparta.logistics.hub_service.hubroute.data.HubRouteData;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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

  private final HubRouteData hubRouteData = new HubRouteData();

  private final HubRouteService hubRouteService;

  // 허브 루트 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubRouteCreateResponseDto>> createHubRoute(
      @RequestBody HubRouteCreateRequestDto requestDto) {
    HubRouteCreateResponseDto responseDto = hubRouteService.createHubRoute(requestDto);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 단일 조회
  @GetMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteDetailResponseDto>> getHubRouteDetail(@PathVariable UUID hubRoutesId) {
    HubRouteDetailResponseDto responseDto = hubRouteService.getHubRouteDetail(hubRoutesId);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 조회 및 검색
  @GetMapping
  public List<HubRouteListResponseDto> getHubRouteList(
      @RequestParam(required = false) String startHubName,
      @RequestParam(required = false) String endHubName) {

    return hubRouteData.hubRouteDatabase.stream()
        .filter(hub -> (startHubName == null || hub.getStartHubName().contains(startHubName)) &&
            (endHubName == null || hub.getEndHubName().contains(endHubName)))
        .collect(Collectors.toList());

  }

  //hubRouteUpdateDatabase
  // 허브 루트 수정
  @PutMapping("/{hubRoutesId}")
  public HubRouteUpdateResponseDto updateHubRoute(@PathVariable Long hubRoutesId,
      @RequestBody HubRouteUpdateRequestDto requestDto) {
    HubRouteDetailResponseDto existingHub = hubRouteData.hubRouteUpdateDatabase.stream()
//        .filter(hub -> hub.getId() == hubRoutesId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));

    // 수정
//    existingHub.setStartHubName(requestDto.getStartHubName());
//    existingHub.setEndHubName(requestDto.getEndHubName());
//    existingHub.setDeliveryDistance(requestDto.getDeliveryDistance());
//    existingHub.setDeliveryTime(requestDto.getDeliveryTime());

    // 반환
    return HubRouteUpdateResponseDto.builder()
        .startHubName("서울특별시 센터")
        .endHubName("부산광역시 센터")
        .deliveryTime(350L)
        .deliveryDistance(400L)
        .build();
  }

  // 허브 루트 삭제
  @DeleteMapping("/{hubRoutesId}")
  public String deleteHubRoute(@PathVariable Long hubRoutesId) {
    HubRouteDetailResponseDto existingHub = hubRouteData.hubRouteDeleteDatabase.stream()
//        .filter(hub -> hub.getId() == hubRoutesId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));
    String startHubName = existingHub.getStartHubName();
    String endHubName = existingHub.getEndHubName();
    return "from " + startHubName + " to " + endHubName + " delete success";
  }
}
