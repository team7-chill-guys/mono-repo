package com.sparta.logistics.delivery_service.application.service.mock;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockCompanyService {
    public UUID getHubId(UUID companyId) {
        return UUID.randomUUID();
    }
}
