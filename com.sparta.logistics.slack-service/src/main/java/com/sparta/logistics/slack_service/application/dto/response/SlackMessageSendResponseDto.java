package com.sparta.logistics.slack_service.application.dto.response;

import lombok.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackMessageSendResponseDto {
    private UUID id;
    private String channel;
    private String result;
    private Timestamp createdAt;
    private Long createdBy;
}
