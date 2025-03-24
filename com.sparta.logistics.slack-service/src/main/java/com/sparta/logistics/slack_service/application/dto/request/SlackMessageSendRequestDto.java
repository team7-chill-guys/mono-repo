package com.sparta.logistics.slack_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class SlackMessageSendRequestDto {
    private UUID id;
    private String slackId;
    private String text;
}
