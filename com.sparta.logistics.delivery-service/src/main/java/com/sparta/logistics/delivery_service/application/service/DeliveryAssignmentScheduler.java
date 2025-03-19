package com.sparta.logistics.delivery_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryAssignmentScheduler {

    private final DeliveryService deliveryService;

    @Scheduled(fixedRate = 60000) //1분
    public void runAssignmentScheduler() {
        deliveryService.assignPendingDeliveries();
    }
}
