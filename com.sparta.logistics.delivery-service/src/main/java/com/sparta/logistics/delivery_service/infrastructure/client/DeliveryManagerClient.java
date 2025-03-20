package com.sparta.logistics.delivery_service.infrastructure.client;

import com.sparta.logistics.delivery_service.application.dto.DeliveryManagerInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="delivery-manager-service")
public interface DeliveryManagerClient {

    @GetMapping("/api/delivery-managers/assign")
    DeliveryManagerInfoDto assignDeliveryManager(@RequestParam UUID startHubId,
                                                 @RequestParam UUID endHubId,
                                                 @RequestParam String type);
}
