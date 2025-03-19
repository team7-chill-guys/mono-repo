package com.sparta.logistics.delivery_service.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="delivery-manager-service")
public interface DeliveryManagerClient {

    @GetMapping("/api/delivery-managers")
    Long getDeliveryManager(@RequestParam UUID departureHubId,
                            @RequestParam String type);
}
