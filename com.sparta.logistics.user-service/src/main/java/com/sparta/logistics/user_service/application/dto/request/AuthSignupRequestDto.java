package com.sparta.logistics.user_service.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthSignupRequestDto {

    private String username;
    private String password;
    private String slackId;

}
