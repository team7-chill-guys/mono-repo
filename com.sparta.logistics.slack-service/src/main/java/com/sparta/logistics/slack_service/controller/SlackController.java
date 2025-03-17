package com.sparta.logistics.slack_service.controller;

import com.sparta.logistics.slack_service.dto.SlackMessageCreateRequestDto;
import com.sparta.logistics.slack_service.dto.SlackMessageCreateResponseDto;
import com.sparta.logistics.slack_service.dto.SlackMessageSendDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/slack")
public class SlackController {
    @PostMapping
    public SlackMessageCreateResponseDto createMessage(@RequestBody SlackMessageCreateRequestDto requestDto) {
        SlackMessageCreateResponseDto responseDto = SlackMessageCreateResponseDto.builder()
                .userId(requestDto.getUserId())
                .notificationData(requestDto.getNotificationData())
                .build();

        return responseDto;
    }

    @PostMapping("/send")
    public SlackMessageSendDto sendMessage(@RequestBody SlackMessageSendDto requestDto) {
        LocalDateTime finalShippingTime = LocalDateTime.of(2025, 3, 16, 9, 0, 0, 0);
        Timestamp finalShippingDeadline = Timestamp.valueOf(finalShippingTime);

        SlackMessageSendDto responseDto = SlackMessageSendDto.builder()
                .userId(requestDto.getUserId())
                .notificationData(requestDto.getNotificationData())
                .finalShippingDeadline(finalShippingDeadline)
                .build();

        return responseDto;
    }
}
