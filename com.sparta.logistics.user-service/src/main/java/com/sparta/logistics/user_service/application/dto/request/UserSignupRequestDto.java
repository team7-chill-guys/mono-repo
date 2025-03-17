package com.sparta.logistics.user_service.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupRequestDto {

    private UUID authId;
    private String username;
    private String slackId;

}
