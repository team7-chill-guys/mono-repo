package com.sparta.logistics.hub_service.presentation.controller;

import com.sparta.logistics.hub_service.data.HubRouteData;
import com.sparta.logistics.hub_service.application.dto.CreateHubRouteRequestDto;
import com.sparta.logistics.hub_service.application.dto.CreateHubRouteResponseDto;
import com.sparta.logistics.hub_service.application.dto.HubRouteDetailResponseDto;
import com.sparta.logistics.hub_service.application.dto.HubRouteListResponseDto;
import com.sparta.logistics.hub_service.application.dto.UpdateHubRouteRequestDto;
import com.sparta.logistics.hub_service.application.dto.UpdateHubRouteResponseDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
public class HubRouteController {

  private final HubRouteData hubRouteData = new HubRouteData();

  // 허브 루트 생성
  @PostMapping
  public CreateHubRouteResponseDto createHubRoute(
      @RequestBody CreateHubRouteRequestDto requestDto) {
    UUID startHubId = UUID.randomUUID(); // 자동 생성
    UUID endHubId = UUID.randomUUID(); // 자동 생성

    CreateHubRouteResponseDto responseDto = CreateHubRouteResponseDto.builder()
        .startHubId(startHubId)
        .endHubId(endHubId)
        .startHubName(requestDto.getStartHubName())
        .endHubName(requestDto.getEndHubName())
        .deliveryTime(requestDto.getDeliveryTime())
        .deliveryDistance(requestDto.getDeliveryDistance())
        .build();

    return responseDto;
  }

  // 허브 루트 단일 조회
  @GetMapping("/{hubRoutesId}")
  public HubRouteDetailResponseDto getHubRouteDetail(@PathVariable Long hubRoutesId) {

    return hubRouteData.hubRouteDetailDatabase.stream()
        .filter(hub -> hub.getId() == hubRoutesId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));
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
  public UpdateHubRouteResponseDto updateHubRoute(@PathVariable Long hubRoutesId,
      @RequestBody UpdateHubRouteRequestDto requestDto) {
    HubRouteDetailResponseDto existingHub = hubRouteData.hubRouteUpdateDatabase.stream()
        .filter(hub -> hub.getId() == hubRoutesId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));

    // 수정
    existingHub.setStartHubName(requestDto.getStartHubName());
    existingHub.setEndHubName(requestDto.getEndHubName());
    existingHub.setDeliveryDistance(requestDto.getDeliveryDistance());
    existingHub.setDeliveryTime(requestDto.getDeliveryTime());

    // 반환
    return UpdateHubRouteResponseDto.builder()
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
        .filter(hub -> hub.getId() == hubRoutesId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));
    String startHubName = existingHub.getStartHubName();
    String endHubName = existingHub.getEndHubName();
    return "from " + startHubName + " to " + endHubName + " delete success";
  }
}
