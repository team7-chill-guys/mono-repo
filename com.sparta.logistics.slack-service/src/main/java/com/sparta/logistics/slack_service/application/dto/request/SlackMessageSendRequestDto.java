package com.sparta.logistics.slack_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackMessageSendRequestDto {
    private UUID id;
    private String channel;
    private String text;
}
