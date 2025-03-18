package com.sparta.logistics.user_service.application.service;

import com.sparta.logistics.user_service.application.dto.request.UserPasswordUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.request.UserUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.response.UserSearchMeResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserSearchResponseDto;
import com.sparta.logistics.user_service.application.dto.response.UserUpdateResponseDto;
import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 검색 : 본인
    public UserSearchMeResponseDto searchMeUser(String userIdHeader) {

        Long userId = Long.parseLong(userIdHeader);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("본인 정보를 찾을 수 없습니다. 헤더로 받은 userId : " + userIdHeader));
        return UserSearchMeResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .slackId(user.getSlackId())
            .role(user.getRole().toString())
            .build();
    }

    // 유저 검색 : userId, username
    public UserSearchResponseDto searchUser(Long userId, String username) {

        User user;
        if (userId != null) {
            user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                    "user_id 에 해당하는 유저가 없습니다. 받은 user_id : " + userId));

        } else {
            user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                    "username 에 해당하는 유저가 없습니다. 받은 username : " + username));

        }
        return UserSearchResponseDto.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .slackId(user.getSlackId())
            .role(user.getRole().toString())
            .build();
    }

    // 유저 수정 : 본인 비밀번호 수정
    public void updatePassword(String userIdHeader, UserPasswordUpdateRequestDto requestDto) {
        Long userId = Long.parseLong(userIdHeader);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다. userId : " + userId));

        if (BCrypt.checkpw(requestDto.getOldPassword(), user.getPassword())) {
            user.updatePassword(BCrypt.hashpw(requestDto.getNewPassword(), BCrypt.gensalt()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }

    // MASTER : 유저 프로필 수정
    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. 받은 userId : " + userId));

        user.updateUser(requestDto);
        userRepository.save(user);

        return UserUpdateResponseDto.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .slackId(user.getSlackId())
            .role(user.getRole().toString())
            .build();
    }
}
