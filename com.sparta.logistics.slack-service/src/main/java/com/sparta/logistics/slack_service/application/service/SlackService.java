package com.sparta.logistics.slack_service.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.logistics.slack_service.application.dto.request.SlackMessageSendRequestDto;
import com.sparta.logistics.slack_service.application.dto.response.SlackMessageSaveResponseDto;
import com.sparta.logistics.slack_service.entity.Slack;
import com.sparta.logistics.slack_service.infrastructure.client.AiClient;
import com.sparta.logistics.slack_service.infrastructure.client.HubClient;
import com.sparta.logistics.slack_service.infrastructure.client.SlackClient;
import com.sparta.logistics.slack_service.infrastructure.dto.DeliveryInfoDto;
import com.sparta.logistics.slack_service.infrastructure.dto.HubResponseDto;
import com.sparta.logistics.slack_service.repository.SlackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
@Service
public class SlackService {

    @Value("${slack.bot.token}")
    private String SLACK_BOT_TOKEN;

    private final SlackRepository slackRepository;
    private final AiClient aiClient;
    private final SlackClient slackClient;
    private final HubClient hubClient;

    public SlackService(SlackRepository slackRepository, AiClient aiClient, SlackClient slackClient, HubClient hubClient) {
        this.slackRepository = slackRepository;
        this.aiClient = aiClient;
        this.slackClient = slackClient;
        this.hubClient = hubClient;
    }

    public SlackMessageSaveResponseDto saveSlackMessage(DeliveryInfoDto deliveryResponseDto) {

        // 1. hubId로 hub 주소 찾기
        HubResponseDto hubStartResponseDto = hubClient.getAddressByHubId(deliveryResponseDto.getDepartureHubId());
        HubResponseDto hubEndResponseDto = hubClient.getAddressByHubId(deliveryResponseDto.getDestinationHubId());

        // 2. delivery 정보 + hub 주소를 AI 요청용 string으로 변환
        String inputForAI = convertDeliveryInfoToString(deliveryResponseDto, hubStartResponseDto, hubEndResponseDto);

        // 3. string 으로 AI api에 전달하여 최종 발송 시한 응답값 받기
        String output = aiClient.generateCompletion(inputForAI);

        // 4. Slack message string 생성 (AI 응답 포함해서)
        String slackText = buildSlackText(deliveryResponseDto, hubStartResponseDto, hubEndResponseDto, output);

        // 5. Slack entity 저장
        Slack slack = Slack.builder()
                .id(UUID.randomUUID())
                .slackId(deliveryResponseDto.getSlackId())
                .text(slackText)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        slackRepository.save(slack);

        sendSlackMessage(slack.getId(), slack.getSlackId());

        // 6. 저장된 메시지 DTO로 반환
        return SlackMessageSaveResponseDto.builder()
                .id(slack.getId())
                .slackId(slack.getSlackId())
                .text(slack.getText())
                .build();
    }

    private String convertDeliveryInfoToString(DeliveryInfoDto deliveryDto, HubResponseDto hubStartResponseDto, HubResponseDto hubEndResponseDto) {
        StringBuilder sb = new StringBuilder();

        sb.append("배송 요청 정보:\n");
        sb.append("배송지: ").append(deliveryDto.getAddress()).append("\n");

        sb.append("출발 허브 정보:\n");
        sb.append("출발 허브 이름: ").append(hubStartResponseDto.getHubName()).append("\n");
        sb.append("출발 허브 주소: ").append(hubStartResponseDto.getAddress()).append("\n");

        sb.append("도착 허브 정보:\n");
        sb.append("도착 허브 이름: ").append(hubEndResponseDto.getHubName()).append("\n");
        sb.append("도착 허브 주소: ").append(hubEndResponseDto.getAddress()).append("\n");

        return sb.toString();
    }

    private String buildSlackText(DeliveryInfoDto deliveryResponseDto, HubResponseDto hubStartDto, HubResponseDto hubEndDto, String output) {
        return convertDeliveryInfoToString(deliveryResponseDto, hubStartDto, hubEndDto) + "\nFinal Delivery Time: " + output;
    }

    public void sendSlackMessage(UUID id, String slackId) {
        String responseText = "";

        Slack slack = slackRepository.findById(id).orElse(null);

        if (slack == null) {
            log.info("슬랙 데이터 없음");
        }

        String messageText = slack.getText();

        SlackMessageSendRequestDto dto = SlackMessageSendRequestDto.builder()
                .id(slack.getId())
                .channel(slackId)
                .text(messageText)
                .build();

        try {
            String slackMessage = slackClient.sendSlackMessage("Bearer " + SLACK_BOT_TOKEN, dto);

            log.info("dto-channel: " + dto.getChannel());
            log.info("dto-text: " + dto.getText());
            log.info("slack에 dto 보냈음" + slackMessage);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(slackMessage);

            if (responseJson.get("ok").asBoolean()) {
                responseText = "Message sent successfully!";
            } else {
                responseText = "Slack API 오류 발생: " + slackMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseText = "Slack API 호출 중 예외 발생";
        }

    }
}