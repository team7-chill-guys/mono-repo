package com.sparta.logistics.slack_service.dto;

import lombok.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackMessageSendDto {
    private UUID id;
    private Long userId;
    private Text notificationData;
    private Timestamp finalShippingDeadline;
    private Timestamp sentAt;
}
