package com.sparta.logistics.ai_service.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiResponseDto {
    private Timestamp finalShippingDeadline;
}
