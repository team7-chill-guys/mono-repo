package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.application.service.UserService;
import com.sparta.logistics.user_service.application.dto.response.UserSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me")
    public ResponseEntity<UserSearchResponseDto> getUserMe() {
        UserSearchResponseDto responseDto = UserSearchResponseDto.builder()
            .id(1L)
            .username("testUser")
            .slackId("testSlackId")
            .role("MASTER")
            .build();

        return ResponseEntity.ok(responseDto);
    }


}
