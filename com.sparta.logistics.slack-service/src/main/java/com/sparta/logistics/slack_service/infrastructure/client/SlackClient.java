package com.sparta.logistics.slack_service.infrastructure.client;

import com.sparta.logistics.slack_service.application.dto.request.SlackMessageSendRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "slack-api", url = "${slack.api.url}")
public interface SlackClient {
    @PostMapping("/chat.postMessage")
    String sendSlackMessage(@RequestHeader("Authorization") String authorization,
                            @RequestBody SlackMessageSendRequestDto requestDto);
}
