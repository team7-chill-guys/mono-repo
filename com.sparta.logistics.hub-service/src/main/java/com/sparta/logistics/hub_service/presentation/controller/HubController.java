package com.sparta.logistics.hub_service.presentation.controller;

import com.sparta.logistics.hub_service.data.HubData;
import com.sparta.logistics.hub_service.application.dto.CreateHubRequestDto;
import com.sparta.logistics.hub_service.application.dto.CreateHubResponseDto;
import com.sparta.logistics.hub_service.application.dto.HubDetailResponseDto;
import com.sparta.logistics.hub_service.application.dto.HubListResponseDto;
import com.sparta.logistics.hub_service.application.dto.UpdateHubRequestDto;
import com.sparta.logistics.hub_service.application.dto.UpdateHubResponseDto;
import java.util.List;
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
@RequestMapping("/api/hubs")
public class HubController {

  private final HubData hubData = new HubData();

  // 허브 생성
  @PostMapping
  public CreateHubResponseDto createHub(@RequestBody CreateHubRequestDto requestDto) {

    CreateHubResponseDto responseDto = CreateHubResponseDto.builder()
        .userId(1)
        .hubName(requestDto.getHubName())
        .address(requestDto.getAddress())
        .latitude(requestDto.getLatitude())
        .longitude(requestDto.getLongitude())
        .build();

    return responseDto;
  }

  // 허브 단일 조회
  @GetMapping("/{hubId}")
  public HubDetailResponseDto getHubDetail(@PathVariable Long hubId) {

    return hubData.hubDetailDatabase.stream()
        .filter(hub -> hub.getHubId() == hubId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));

  }

  // 허브 조회 및 검색
  @GetMapping
  public List<HubListResponseDto> getHubList(@RequestParam(required = false) String hubName,
      @RequestParam(required = false) String address) {
    return hubData.hubDatabase.stream()
        .filter(hub -> (hubName == null || hub.getHubName().contains(hubName)) &&
            (address == null || hub.getAddress().contains(address)))
        .collect(Collectors.toList());
  }

  // 허브 수정
  @PutMapping("/{hubId}")
  public UpdateHubResponseDto updateHub(@PathVariable Long hubId,
      @RequestBody UpdateHubRequestDto requestDto) {

    HubDetailResponseDto existingHub = hubData.hubUpdateDatabase.stream()
        .filter(hub -> hub.getHubId() == hubId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));

    // 수정
    existingHub.setHubName(requestDto.getHubName());
    existingHub.setAddress(requestDto.getAddress());
    existingHub.setLatitude(requestDto.getLatitude());
    existingHub.setLongitude(requestDto.getLongitude());

    // 반환
    return UpdateHubResponseDto.builder()
        .hubId(existingHub.getHubId())
        .userId(existingHub.getUserId())
        .hubName(existingHub.getHubName())
        .address(existingHub.getAddress())
        .latitude(existingHub.getLatitude())
        .longitude(existingHub.getLongitude())
        .build();

  }

  // 허브 삭제
  @DeleteMapping("/{hubId}")
  public String deleteHub(@PathVariable Long hubId) {
    HubDetailResponseDto existingHub = hubData.hubDeleteDatabase.stream()
        .filter(hub -> hub.getHubId() == hubId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Hub not found"));
    String hubName = existingHub.getHubName();
    return hubName + " delete success";
  }

}
