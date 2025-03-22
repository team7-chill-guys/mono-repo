package com.sparta.logistics.user_service.application.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequestDto {

    @Size(min = 4, max = 10, message = "new username 은 최소 4자 이상, 10자 이하여야 합니다.")
    @Pattern(
        regexp = "^[a-z0-9]+$",
        message = "new username 은 알파벳 소문자와 숫자로만 구성되어야 합니다."
    )
    private String newUsername;

    private String newSlackId;
}
