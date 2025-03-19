package com.sparta.logistics.order_service.infrastructure.client;

import com.sparta.logistics.order_service.infrastructure.client.dto.request.OrderDeliveryRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    @PostMapping("/api/deliveries")
    UUID createDelivery(@RequestBody OrderDeliveryRequestDto requestDto);
}