package com.sparta.logistics.delivery_service.application.mapper;

import com.sparta.logistics.delivery_service.domain.model.*;

import java.util.UUID;

public class DeliveryRouteMapper {

    public static DeliveryRoute toEntity(UUID deliveryId, Integer sequence, UUID startHubId, UUID endHubId, Double estimatedDistance, Integer estimatedTime, Double actualDistance, Integer actualTime) {
        Distance estimatedDis = new Distance(estimatedDistance);
        Distance actualDis = new Distance(actualDistance);
        Time estimatedTi = new Time(estimatedTime);
        Time actualTi = new Time(actualTime);

        return DeliveryRoute.builder()
                .deliveryId(deliveryId)
                .sequence(sequence)
                .startHudId(startHubId)
                .endHudId(endHubId)
                .estimatedDistance(estimatedDis)
                .actualDistance(actualDis)
                .estimatedTime(estimatedTi)
                .actualTime(actualTi)
                .build();
    }
}
