package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.application.dto.request.AuthLoginRequestDto;
import com.sparta.logistics.user_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.user_service.application.dto.response.AuthSignupResponseDto;
import com.sparta.logistics.user_service.application.dto.response.AuthTokenResponseDto;
import com.sparta.logistics.user_service.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<AuthSignupResponseDto> createUser(@RequestBody AuthSignupRequestDto requestDto,
                                                            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader
    ) {
        AuthSignupResponseDto responseDto = authService.createUser(requestDto, userIdHeader);
        return ResponseEntity.ok(responseDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<AuthTokenResponseDto> loginUser(@RequestBody AuthLoginRequestDto requestDto
    ) {
        AuthTokenResponseDto responseDto = authService.loginUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
