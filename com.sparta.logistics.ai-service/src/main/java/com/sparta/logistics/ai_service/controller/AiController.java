package com.sparta.logistics.ai_service.controller;

import com.sparta.logistics.ai_service.dto.AiRequestDto;
import com.sparta.logistics.ai_service.dto.AiResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    @PostMapping("/calculate-deadline")
    public AiResponseDto calculateFinalShippingDeadline(@RequestBody AiRequestDto aiRequestDto) {
        LocalDateTime finalShippingTime = LocalDateTime.of(2025, 3, 16, 9, 0, 0, 0);
        Timestamp finalShippingDeadline = Timestamp.valueOf(finalShippingTime);

        return AiResponseDto.builder()
                .finalShippingDeadline(finalShippingDeadline)
                .build();
    }
}
