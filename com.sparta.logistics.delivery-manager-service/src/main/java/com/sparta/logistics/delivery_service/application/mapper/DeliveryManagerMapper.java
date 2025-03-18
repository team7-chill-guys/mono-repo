package com.sparta.logistics.delivery_service.application.mapper;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerCreateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryManagerResponseDto;
import com.sparta.logistics.delivery_service.domain.model.DeliveryManager;

public class DeliveryManagerMapper {

    public static DeliveryManager toEntity(DeliveryManagerCreateRequestDto dto) {
        return DeliveryManager.builder()
                .id(dto.getId())
                .hubId(dto.getHubId())
                .slackId(dto.getSlackId())
                .type(dto.getType())
                .sequence(dto.getSequence())
                .build();
    }

    public static DeliveryManagerResponseDto toDto(DeliveryManager deliveryManager) {
        return DeliveryManagerResponseDto.builder()
                .hubId(deliveryManager.getHubId())
                .slackId(deliveryManager.getSlackId())
                .type(deliveryManager.getType())
                .sequence(deliveryManager.getSequence())
                .build();
    }
}
