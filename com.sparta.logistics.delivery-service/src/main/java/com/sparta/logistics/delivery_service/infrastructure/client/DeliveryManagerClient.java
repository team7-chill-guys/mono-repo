package com.sparta.logistics.delivery_service.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="delivery-manager-service")
public interface DeliveryManagerClient {
}
