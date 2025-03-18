package com.sparta.logistics.delivery_service.application.dto.response;

import com.sparta.logistics.delivery_service.domain.model.DeliveryRouteStatus;
import com.sparta.logistics.delivery_service.domain.model.Distance;
import com.sparta.logistics.delivery_service.domain.model.Time;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class DeliveryRouteResponseDto {
    private UUID deliveryId;
    private UUID startHubId;
    private UUID endHubId;
    private Distance estimatedDistance;
    private Distance actualDistance;
    private Time estimatedTime;
    private Time actualTime;
    private DeliveryRouteStatus status;
    private  Long hubDeliveryManagerId;
    private LocalDateTime updatedAt;
}
