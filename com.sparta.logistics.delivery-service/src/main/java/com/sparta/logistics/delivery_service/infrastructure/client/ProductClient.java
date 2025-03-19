package com.sparta.logistics.delivery_service.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="product-service")
public interface ProductClient {

}
