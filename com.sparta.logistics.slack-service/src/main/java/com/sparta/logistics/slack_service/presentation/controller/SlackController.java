package com.sparta.logistics.slack_service.presentation.controller;

import com.sparta.logistics.slack_service.application.dto.request.SlackMessageSendRequestDto;
import com.sparta.logistics.slack_service.application.dto.response.SlackMessageSendResponseDto;
import com.sparta.logistics.slack_service.application.service.SlackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/slack")
public class SlackController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

//    @PostMapping("/save")
//    public SlackMessageSaveResponseDto saveMessage(@RequestBody DeliveryResponseDto deliveryResponseDto) {
//        return slackService.saveSlackMessage(deliveryResponseDto);
//    }

    @PostMapping("/send")
    public SlackMessageSendResponseDto sendMessage(@RequestBody SlackMessageSendRequestDto slackMessageSendRequestDto) {
        return slackService.sendSlackMessage(slackMessageSendRequestDto);
    }
}