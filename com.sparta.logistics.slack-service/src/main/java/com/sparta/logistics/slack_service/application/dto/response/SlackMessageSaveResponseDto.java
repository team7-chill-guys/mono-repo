package com.sparta.logistics.slack_service.application.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackMessageSaveResponseDto {
    private UUID id;
    private String text;
    private Timestamp createdAt;
    private Long createdBy;
}
