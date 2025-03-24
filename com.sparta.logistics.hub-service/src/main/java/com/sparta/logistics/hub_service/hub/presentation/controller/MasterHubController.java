package com.sparta.logistics.hub_service.hub.presentation.controller;

import com.sparta.logistics.hub_service.global.dto.ResponseDto;
import com.sparta.logistics.hub_service.global.utils.JwtUtil;
import com.sparta.logistics.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.request.HubUpdateRequestDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.logistics.hub_service.hub.application.dto.response.HubUpdateResponseDto;
import com.sparta.logistics.hub_service.hub.application.service.HubService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
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

@Slf4j
@RestController
@RequestMapping("/api/master/hubs")
@RequiredArgsConstructor
public class MasterHubController {

  private final HubService hubService;
  private final JwtUtil jwtUtil;

  // 허브 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubCreateResponseDto>> createHub(
      @Valid @RequestBody HubCreateRequestDto requestDto,
      @RequestHeader("Authorization") String authHeader) {

    String token = authHeader.replace("Bearer ", "");

    Claims claims = jwtUtil.getUserInfoFromToken(token);
    String userIdHeader = claims.get("sub", String.class);

    log.info("JWT에서 추출한 userId: {}", userIdHeader);

    HubCreateResponseDto responseDto = hubService.createHub(requestDto, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 수정
  @PutMapping("/{hubId}")
  public ResponseEntity<ResponseDto<HubUpdateResponseDto>> updateHub(@PathVariable UUID hubId,
      @Valid @RequestBody HubUpdateRequestDto requestDto,
      @RequestHeader("Authorization") String authHeader) {

    String token = authHeader.replace("Bearer ", "");

    Claims claims = jwtUtil.getUserInfoFromToken(token);
    String userIdHeader = claims.get("sub", String.class);

    HubUpdateResponseDto responseDto = hubService.updateHub(hubId, requestDto, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 삭제
  @DeleteMapping("/{hubId}")
  public ResponseEntity<?> deleteHub(Long userId, @PathVariable UUID hubId,
      @RequestHeader("Authorization") String authHeader) {

    String token = authHeader.replace("Bearer ", "");

    Claims claims = jwtUtil.getUserInfoFromToken(token);
    String userIdHeader = claims.get("sub", String.class);

    hubService.deleteHub(userId, hubId, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success("delete success"));
  }
}
