package com.sparta.logistics.auth_service.application.service;

import com.sparta.logistics.auth_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.auth_service.application.dto.request.UserSignupRequestDto;
import com.sparta.logistics.auth_service.application.dto.response.AuthSignupResponseDto;
import com.sparta.logistics.auth_service.domain.entity.Auth;
import com.sparta.logistics.auth_service.domain.repository.AuthRepository;
import com.sparta.logistics.auth_service.presentation.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserFeignClient userFeignClient;

    public AuthSignupResponseDto signup(AuthSignupRequestDto requestDto) {
        String hashedPassword = BCrypt.hashpw(requestDto.getPassword(), BCrypt.gensalt());
        Auth auth = Auth.fromRequest(requestDto, hashedPassword);
        authRepository.save(auth);

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
            .authId(auth.getAuthId().toString())
            .username(auth.username)
            .slackId(requestDto.getSlackId())
            .build();

//        userFeignClient.createUser(userSignupRequestDto);

        return AuthSignupResponseDto.builder()
            .username(auth.username)
            .build();
    }

}
