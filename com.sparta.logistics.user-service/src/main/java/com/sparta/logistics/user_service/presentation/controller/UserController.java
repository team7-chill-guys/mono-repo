package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.presentation.dto.response.UserSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

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
