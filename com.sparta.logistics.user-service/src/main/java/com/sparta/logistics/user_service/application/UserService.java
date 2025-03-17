package com.sparta.logistics.user_service.application;

import com.sparta.logistics.user_service.application.dto.request.UserSignupRequestDto;
import com.sparta.logistics.user_service.application.dto.response.UserSignupResponseDto;
import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSignupResponseDto createUser(UserSignupRequestDto requestDto) {
        User user = User.builder()
            .authId(requestDto.getAuthId())
            .username(requestDto.getUsername())
            .slackId(requestDto.getSlackId())
            .build();

        userRepository.save(user);

        return UserSignupResponseDto.builder()
            .username(user.getUsername())
            .slackId(user.getSlackId())
            .build();
    }
}
