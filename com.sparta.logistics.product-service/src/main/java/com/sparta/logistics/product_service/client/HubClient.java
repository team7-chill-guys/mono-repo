package com.sparta.logistics.product_service.client;

import com.sparta.logistics.product_service.client.dto.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {
    @GetMapping("/api/hubs/{hubId}")
    HubResponseDto getHub(@PathVariable("hubId") UUID hubId);
}