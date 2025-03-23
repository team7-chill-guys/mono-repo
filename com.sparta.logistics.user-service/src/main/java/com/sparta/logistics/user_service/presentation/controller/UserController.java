package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.application.dto.request.UserPasswordUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.request.UserRoleUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.request.UserUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.response.UserRoleSearchResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserRoleUpdateResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserSearchMeResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserSearchResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserUpdateResponseDto;
import com.sparta.logistics.user_service.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 유저 검색 : 본인
    @GetMapping("/users/me")
    public ResponseEntity<UserSearchMeResponseDto> getUserMe(@RequestHeader(value = "X-User-Id", required = false) String userIdHeader
    ) {
        if (userIdHeader == null) {
            log.warn("header로 받은 정보에 X-User-Id가 존재하지 않습니다. X-User-Id : {}", userIdHeader);
            return ResponseEntity.badRequest().build();
        }
        UserSearchMeResponseDto responseDto = userService.searchMeUser(userIdHeader);
        return ResponseEntity.ok(responseDto);
    }

    // 유저 검색 : userId, username 기반. 동시 검색 X
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

    // 유저 권한 기반 조회.
    @GetMapping("/users/role")
    public ResponseEntity<Page<UserRoleSearchResponseDto>> roleSearchUser(@RequestParam(required = true) String userRole,
                                                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                          @RequestParam(required = false, defaultValue = "DESC") String direction,
                                                                          @PageableDefault(
                                                                              page = 0,
                                                                              size = 10) Pageable pageable
    ) {

        // 페이지 사이즈가 10, 30, 50이 아닐 경우 디폴트 값인 10으로 설정
        int pageSize = pageable.getPageSize();
        if (pageSize != 10 && pageSize != 30 && pageSize != 50) {
            pageSize = 10;
        }

        // 정렬 기준에 updatedAt이 들어온다면 updatedAt을 기준으로 정렬, 그렇지 않은 모든 요청은 createdAt 으로 정렬
        String finalSortBy = "updatedAt".equalsIgnoreCase(sortBy)
            ? "updatedAt"
            : "createdAt";

        // 내림차순, 오름차순 정렬시 ASC 가 들어온다면 오름차순으로 정렬, 그렇지 않다면 DESC 로 내림차순으로 정렬
        Sort.Direction finalDirection = "ASC".equalsIgnoreCase(direction)
            ? Direction.ASC
            : Direction.DESC;

        // 위에서 조건별로 설정한 데이터를 담은 Pageable 구현체를 생성하여 서비스에 전달
        Pageable adjustPageable = PageRequest.of(
            pageable.getPageNumber(),
            pageSize,
            Sort.by(finalDirection, finalSortBy)
        );

        Page<UserRoleSearchResponseDto> responseDtoList = userService.roleSearchUser(userRole, adjustPageable);
        return ResponseEntity.ok(responseDtoList);
    }

    // 유저 수정 : 본인 비밀번호 수정
    @PutMapping("/users/password")
    public ResponseEntity<String> updatePassword(@RequestHeader(value = "X-User-Id") String userIdHeader,
                                                 @Valid @RequestBody UserPasswordUpdateRequestDto requestDto
    ) {
        userService.updatePassword(userIdHeader, requestDto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    // MASTER : 유저 프로필 수정
    @PutMapping("/master/users/{userId}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable("userId") Long userId,
                                                            @RequestHeader(value = "X-User-Id") String userIdHeader,
                                                            @Valid @RequestBody UserUpdateRequestDto requestDto
    ) {
        UserUpdateResponseDto responseDto = userService.updateUser(userId, requestDto, userIdHeader);
        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 유저 권한 수정
    @PutMapping("/master/users/role/{userId}")
    public ResponseEntity<UserRoleUpdateResponseDto> roleUpdateUser(@PathVariable("userId") Long userId,
                                                                    @RequestHeader(value = "X-User-Id") String userIdHeader,
                                                                    @RequestBody UserRoleUpdateRequestDto requestDto
        ) {
        UserRoleUpdateResponseDto responseDto = userService.roleUpdateUser(userId, userIdHeader, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 회원 탈퇴
    @DeleteMapping("/master/users/{userId}")
    public ResponseEntity<String> deleteUser(@RequestHeader(value = "X-User-Id") String userIdHeader,
                                             @PathVariable("userId") Long userId
    ) {
        userService.deleteUser(userIdHeader, userId);
        return ResponseEntity.ok("유저가 삭제되었습니다 userId : " + userId);
    }

}
