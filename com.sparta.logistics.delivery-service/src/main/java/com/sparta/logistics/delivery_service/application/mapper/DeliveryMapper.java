package com.sparta.logistics.delivery_service.application.mapper;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryCreateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryResponseDto;
import com.sparta.logistics.delivery_service.domain.model.Delivery;
import com.sparta.logistics.delivery_service.domain.model.RecipientCompany;

import java.util.UUID;

public class DeliveryMapper {

    public static Delivery toEntity(DeliveryCreateRequestDto dto,
                                    UUID departureHubId,
                                    UUID destinationHubId){
        RecipientCompany recipientCompany = RecipientCompany.builder()
                .companyId(dto.getCompanyId())
                .address(dto.getAddress())
                .slackId(dto.getSlackId())
                .phone(dto.getPhoneNumber())
                .build();

        return Delivery.builder()
                .orderId(dto.getOrderId())
                .departureHubId(departureHubId)
                .destinationHubId(destinationHubId)
                .recipientCompany(recipientCompany)
                .build();
    }

    public static DeliveryResponseDto toDto (Delivery delivery){
        return DeliveryResponseDto.builder()
                .orderId(delivery.getOrderId())
                .departureHubId(delivery.getDepartureHubId())
                .destinationHubId(delivery.getDestinationHubId())
                .recipientCompany(delivery.getRecipientCompany())
                .build();
    }
}
