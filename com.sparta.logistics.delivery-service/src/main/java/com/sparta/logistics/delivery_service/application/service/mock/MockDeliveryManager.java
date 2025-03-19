package com.sparta.logistics.delivery_service.application.service.mock;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockDeliveryManager {
    public Long getDeliveryManager(UUID departureHubId) {
        return 1342L;
    }
}
