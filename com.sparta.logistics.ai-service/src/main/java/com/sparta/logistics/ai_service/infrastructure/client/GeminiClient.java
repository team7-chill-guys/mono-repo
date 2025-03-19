package com.sparta.logistics.ai_service.infrastructure.client;

import com.example.demo.infrastructure.dto.request.GeminiRequestDto;
import com.example.demo.infrastructure.dto.response.GeminiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gemini-api", url = "${gemini.baseurl}")
public interface GeminiClient {
    @PostMapping("/v1beta/models/gemini-2.0-flash:generateContent")
    GeminiResponseDto getCompletion(
            @RequestBody GeminiRequestDto request,
            @RequestParam("key") String apiKey
    );
}
