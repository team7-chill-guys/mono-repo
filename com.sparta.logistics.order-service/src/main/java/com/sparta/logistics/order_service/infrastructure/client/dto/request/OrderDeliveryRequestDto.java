package com.sparta.logistics.order_service.infrastructure.client.dto.request;

import com.sparta.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeliveryRequestDto {
    private UUID orderId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;

    public static OrderDeliveryRequestDto fromOrderRequest(OrderCreateRequestDto requestDto, UUID orderId) {
        return OrderDeliveryRequestDto.builder()
                .orderId(orderId)
                .requestCompanyId(requestDto.getRequestCompanyId())
                .responseCompanyId(requestDto.getResponseCompanyId())
                .build();
    }
}


