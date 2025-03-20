package com.sparta.logistics.delivery_service.application.mapper;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerCreateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryManagerResponseDto;
import com.sparta.logistics.delivery_service.domain.model.DeliveryManager;

public class DeliveryManagerMapper {

    public static DeliveryManager toEntity(DeliveryManagerCreateRequestDto dto, Long id, Long sequence) {
        return DeliveryManager.builder()
                .id(id)
                .hubId(dto.getHubId())
                .slackId(dto.getSlackId())
                .type(dto.getType())
                .sequence(sequence)
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
