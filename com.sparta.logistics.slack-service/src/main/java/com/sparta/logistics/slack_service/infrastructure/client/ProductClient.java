package com.sparta.logistics.slack_service.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{productId}/name")
    String getProductName(@RequestBody UUID productId);
}
