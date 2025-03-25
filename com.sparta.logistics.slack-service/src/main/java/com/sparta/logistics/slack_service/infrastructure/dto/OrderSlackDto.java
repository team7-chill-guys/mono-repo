package com.sparta.logistics.slack_service.infrastructure.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class OrderSlackDto {
    private Long quantity;
    private String request;
}
