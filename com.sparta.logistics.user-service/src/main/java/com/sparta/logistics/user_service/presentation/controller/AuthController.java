package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.user_service.application.dto.response.AuthSignupResponseDto;
import com.sparta.logistics.user_service.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthSignupResponseDto> createUser(@RequestBody AuthSignupRequestDto requestDto) {
        AuthSignupResponseDto responseDto = authService.createUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
