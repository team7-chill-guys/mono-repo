package com.sparta.logistics.auth_service.application.dto.request;

import lombok.Getter;

@Getter
public class AuthSignupRequestDto {

    private String username;
    private String password;
    private String slackId;

}
