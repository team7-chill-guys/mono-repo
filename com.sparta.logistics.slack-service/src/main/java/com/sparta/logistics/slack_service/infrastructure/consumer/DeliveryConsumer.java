package com.sparta.logistics.slack_service.infrastructure.consumer;

import com.sparta.logistics.slack_service.application.service.SlackService;
import com.sparta.logistics.slack_service.infrastructure.dto.DeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryConsumer {
    private final SlackService slackService;
    @KafkaListener(topics = "delivery-topic")
    public void listen(DeliveryResponseDto responseDto) {
        slackService.saveSlackMessage(responseDto);
    }
}
