package com.sparta.logistics.product_service.client.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubResponseDto {
    private UUID id;
    private Long userId;
    private String hubName;
    private String address;
    private Double latitude;
    private Double longitude;
}
