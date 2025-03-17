package com.sparta.logistics.auth_service.infrastructure.dto.response;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupResponseDto {

    private String username;
    private String slackId;

}
