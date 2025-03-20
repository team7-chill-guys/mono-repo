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
    private UUID startHubId;
    private UUID endHubId;
    private String recipientName;
    private String address;
    private String notes;
}