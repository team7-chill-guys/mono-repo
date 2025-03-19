package com.sparta.logistics.slack_service.infrastructure.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponseDto {
    private UUID deliveryId;
    private String status;
    private String recipientName;
    private String address;
    private String deliveryTime;
    private String notes;
}
