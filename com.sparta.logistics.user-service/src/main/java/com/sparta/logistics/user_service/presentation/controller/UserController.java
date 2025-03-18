package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.application.dto.response.UserSearchMeResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserSearchResponseDto;
import com.sparta.logistics.user_service.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me")
    public ResponseEntity<UserSearchMeResponseDto> getUserMe() {
        UserSearchMeResponseDto responseDto = UserSearchMeResponseDto.builder()
            .id(1L)
            .username("testUser")
            .slackId("testSlackId")
            .role("MASTER")
            .build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users/search")
    public ResponseEntity<UserSearchResponseDto> searchUser(@RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) String username
        ) {

        if (username == null && userId == null) {
            log.warn("username 과 userId가 동시에 입력되었습니다.");
            return ResponseEntity.badRequest().build();

        } else if (username != null && userId != null) {
            log.warn("username 과 userId가 비어있습니다.");
            return ResponseEntity.badRequest().build();
        }

        UserSearchResponseDto responseDto = userService.searchUser(userId, username);
        return ResponseEntity.ok(responseDto);
    }

}
