package com.sparta.logistics.user_service.application.service;


import com.sparta.logistics.user_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.user_service.application.dto.response.AuthSignupResponseDto;
import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.entity.UserRole;
import com.sparta.logistics.user_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // 회원가입
    public AuthSignupResponseDto createUser(AuthSignupRequestDto requestDto) {
        User user = User.builder()
            .username(requestDto.getUsername())
            .password(BCrypt.hashpw(requestDto.getPassword(), BCrypt.gensalt()))
            .slackId(requestDto.getSlackId())
            .role(UserRole.ROLE_USER)
            .build();

        userRepository.save(user);

        return AuthSignupResponseDto.builder()
            .username(user.getUsername())
            .slackId(user.getSlackId())
            .build();
    }

}
