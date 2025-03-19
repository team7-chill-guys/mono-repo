package com.sparta.logistics.user_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    private Long id;
    private String username;
    private String slackId;
    private String role;
    private String password;

}
