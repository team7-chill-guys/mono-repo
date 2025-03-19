package com.sparta.logistics.slack_service.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.logistics.slack_service.application.dto.request.SlackMessageSendRequestDto;
import com.sparta.logistics.slack_service.application.dto.response.SlackMessageSaveResponseDto;
import com.sparta.logistics.slack_service.application.dto.response.SlackMessageSendResponseDto;
import com.sparta.logistics.slack_service.entity.Slack;
import com.sparta.logistics.slack_service.infrastructure.client.AiClient;
import com.sparta.logistics.slack_service.infrastructure.client.SlackClient;
import com.sparta.logistics.slack_service.infrastructure.dto.DeliveryResponseDto;
import com.sparta.logistics.slack_service.repository.SlackRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class SlackService {

    @Value("${slack.bot.token}")
    private String SLACK_BOT_TOKEN;

    private final SlackRepository slackRepository;
    private final AiClient aiClient;
    private final SlackClient slackClient;

    public SlackService(SlackRepository slackRepository, AiClient aiClient, SlackClient slackClient) {
        this.slackRepository = slackRepository;
        this.aiClient = aiClient;
        this.slackClient = slackClient;
    }


    public SlackMessageSaveResponseDto saveSlackMessage(DeliveryResponseDto deliveryResponseDto) {
        // 1. dto 값 AI 에 전달할 요청값 string 으로 변환
        String inputForAI = convertDeliveryInfoToString(deliveryResponseDto);

        // 2. string 으로 AI api에 전달하여 최종 발송 시한 응답값 받기
        String output = aiClient.generateCompletion(inputForAI);

        // 3. Slack message string 생성 (AI 응답 포함해서)
        String slackText = buildSlackText(deliveryResponseDto, output);

        // 4. Slack entity 저장
        Slack slack = Slack.builder()
                .id(UUID.randomUUID())
                .text(slackText)
                .build();

        slackRepository.save(slack);

        // 5. 저장된 메시지 DTO로 반환
        return SlackMessageSaveResponseDto.builder()
                .id(slack.getId())
                .text(slack.getText())
                .build();
    }

    private String convertDeliveryInfoToString(DeliveryResponseDto deliveryResponseDto) {
        StringBuilder sb = new StringBuilder();

        sb.append("Delivery ID: ").append(deliveryResponseDto.getDeliveryId()).append("\n")
                .append("Status: ").append(deliveryResponseDto.getStatus()).append("\n")
                .append("Recipient Name: ").append(deliveryResponseDto.getRecipientName()).append("\n")
                .append("Address: ").append(deliveryResponseDto.getAddress()).append("\n")
                .append("Scheduled Delivery Time: ").append(deliveryResponseDto.getDeliveryTime()).append("\n")
                .append("Notes: ").append(deliveryResponseDto.getNotes());

        return sb.toString();
    }

    private String buildSlackText(DeliveryResponseDto deliveryResponseDto, String output) {
        return convertDeliveryInfoToString(deliveryResponseDto) + "\nFinal Delivery Time: " + output;
    }

    public SlackMessageSendResponseDto sendSlackMessage(SlackMessageSendRequestDto requestDto) {
        String responseText = "";

        try {
            String response = slackClient.sendSlackMessage("Bearer " + SLACK_BOT_TOKEN, requestDto);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(response);

            if (responseJson.get("ok").asBoolean()) {
                responseText = "Message sent successfully!";
            } else {
                responseText = "Slack API 오류 발생: " + response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseText = "Slack API 호출 중 예외 발생";
        }

        return SlackMessageSendResponseDto.builder()
                .id(requestDto.getId())
                .channel(requestDto.getChannel())
                .result(responseText)
                .build();
    }
}
