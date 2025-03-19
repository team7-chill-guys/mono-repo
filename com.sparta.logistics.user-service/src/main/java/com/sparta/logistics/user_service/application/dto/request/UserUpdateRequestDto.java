package com.sparta.logistics.user_service.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequestDto {
    private String newUsername;
    private String newSlackId;
    private String newRole;
}
