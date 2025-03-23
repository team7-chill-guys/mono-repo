package com.sparta.logistics.user_service.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchAllResponseDto {

    private Long userId;
    private String username;
    private String slackId;
    private String role;

}
