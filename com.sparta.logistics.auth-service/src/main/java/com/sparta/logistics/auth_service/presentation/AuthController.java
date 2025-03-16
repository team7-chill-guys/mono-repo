package com.sparta.logistics.auth_service.presentation;

import com.sparta.logistics.auth_service.application.service.AuthService;
import com.sparta.logistics.auth_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.auth_service.application.dto.response.AuthSignupResponseDto;
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

    @PostMapping("/users/signup")
    public ResponseEntity<AuthSignupResponseDto> userSignup(@RequestBody AuthSignupRequestDto requestDto) {
        AuthSignupResponseDto responseDto = authService.signup(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
