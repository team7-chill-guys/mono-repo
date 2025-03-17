package com.sparta.logistics.user_service.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
