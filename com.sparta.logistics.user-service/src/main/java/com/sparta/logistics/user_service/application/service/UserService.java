package com.sparta.logistics.user_service.application.service;

import com.sparta.logistics.user_service.application.dto.response.UserSearchResponseDto;
import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
