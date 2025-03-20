package com.sparta.logistics.delivery_service.application.mapper;

import com.sparta.logistics.delivery_service.application.dto.response.DeliveryManagerInfoDto;

public class DeliveryManagerInfoMapper {

    public static DeliveryManagerInfoDto toDto(Long id, String slackId) {
        return DeliveryManagerInfoDto.builder()
                .id(id)
                .slackId(slackId)
                .build();
    }
}
