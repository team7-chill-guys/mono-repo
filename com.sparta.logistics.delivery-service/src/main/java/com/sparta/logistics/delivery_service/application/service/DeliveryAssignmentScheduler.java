package com.sparta.logistics.delivery_service.application.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryAssignmentScheduler {

    private final DeliveryService deliveryService;
    private final DeliveryRouteService deliveryRouteService;
    private boolean shutdown = false;

    @Scheduled(fixedRate = 60000) //1분
    public void runAssignmentScheduler() {
        if(shutdown) return;

        try {
            deliveryService.assignPendingDeliveries();
            deliveryRouteService.assignPendingDeliveries();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        shutdown = true;
    }
}
