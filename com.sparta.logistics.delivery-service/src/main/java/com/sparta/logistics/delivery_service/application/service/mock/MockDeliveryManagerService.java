package com.sparta.logistics.delivery_service.application.service.mock;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockDeliveryManagerService {
    public Long getDeliveryManager(UUID departureHubId, String type) {
        return 123L;
    }
}
