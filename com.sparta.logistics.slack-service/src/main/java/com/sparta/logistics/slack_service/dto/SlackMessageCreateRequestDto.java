package com.sparta.logistics.slack_service.dto;

import lombok.*;
import org.w3c.dom.Text;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackMessageCreateRequestDto {
    private Long userId;
    private Text notificationData;
}
