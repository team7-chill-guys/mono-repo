package com.sparta.logistics.slack_service.infrastructure.client;

import com.sparta.logistics.slack_service.infrastructure.dto.DeliveryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "delivery-api")
public interface DeliveryClient {
    @GetMapping("/api/deliveries/{deliveryId}")
    DeliveryResponseDto getDeliveryById(@PathVariable("deliveryId") UUID deliveryId);
}
