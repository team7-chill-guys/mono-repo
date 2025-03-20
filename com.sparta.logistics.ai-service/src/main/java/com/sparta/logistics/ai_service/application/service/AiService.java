package com.sparta.logistics.ai_service.application.service;

import com.sparta.logistics.ai_service.infrastructure.client.GeminiClient;
import com.sparta.logistics.ai_service.infrastructure.dto.request.GeminiRequestDto;
import com.sparta.logistics.ai_service.infrastructure.dto.response.GeminiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final GeminiClient geminiClient;

    public AiService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    @Value("${googleai.api.key}")
    private String GEMINI_API_KEY;

    public String getCompletionText(String inputForAI) {
        String request = inputForAI + "에 대한 배송 시간을 계산하여 정확한 배송 일자를 50자 이내로 작성해주세요.";
        GeminiRequestDto requestDto = new GeminiRequestDto(request);

        GeminiResponseDto responseDto = geminiClient.getCompletion(requestDto, GEMINI_API_KEY);

        if (responseDto.getCandidates() != null && !responseDto.getCandidates().isEmpty()) {
            String text = responseDto.getCandidates().get(0).getContent().getParts().get(0).getText();
            return text;
        }
        return "답변을 받을 수 없습니다.";
    }
}