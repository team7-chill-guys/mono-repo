package com.sparta.logistics.user_service.presentation.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-manager-service")
public interface DeliveryManagerFeignClient {

    @PostMapping("/api/delivery-manager")
    ResponseEntity<Void> createDeliveryManager(@RequestParam("id") Long id,
                                               @RequestParam("slackId") String slackId
    );

}
