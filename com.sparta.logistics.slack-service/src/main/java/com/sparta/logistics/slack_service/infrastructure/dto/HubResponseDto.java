package com.sparta.logistics.slack_service.infrastructure.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubResponseDto {
    private String name;
    private String address;
}
