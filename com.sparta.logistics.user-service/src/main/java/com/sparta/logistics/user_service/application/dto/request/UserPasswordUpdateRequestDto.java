package com.sparta.logistics.user_service.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPasswordUpdateRequestDto {
    private String oldPassword;
    private String newPassword;
}
