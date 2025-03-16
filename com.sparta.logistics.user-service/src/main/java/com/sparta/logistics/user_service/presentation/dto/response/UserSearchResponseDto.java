package com.sparta.logistics.user_service.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSearchResponseDto {
    private Long id;
    private String username;
    private String slackId;
    private String role;

    @Builder
    public UserSearchResponseDto(
        Long id,
        String username,
        String slackId,
        String role
    ) {
        this.id = id;
        this.username = username;
        this.slackId = slackId;
        this.role = role;
    }

}
