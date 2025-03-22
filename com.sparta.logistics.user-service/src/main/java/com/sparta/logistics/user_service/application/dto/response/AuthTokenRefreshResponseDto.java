package com.sparta.logistics.user_service.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokenRefreshResponseDto {

    private String newAccessToken;
    private String newRefreshToken;

}
