package com.sparta.logistics.delivery_service.application.mapper;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryInfoDto;
import com.sparta.logistics.delivery_service.domain.model.Delivery;

public class DeliveryInfoMapper {

    public static DeliveryInfoDto toDto(Delivery delivery) {
        return DeliveryInfoDto.builder()
                .departureHubId(delivery.getDepartureHubId())
                .destinationHubId(delivery.getDestinationHubId())
                .deliveryManagerId(delivery.getDeliveryManagerId())
                .orderId(delivery.getOrderId())
                .slackId(delivery.getRecipientCompany().getSlackId())
                .build();
    }
}