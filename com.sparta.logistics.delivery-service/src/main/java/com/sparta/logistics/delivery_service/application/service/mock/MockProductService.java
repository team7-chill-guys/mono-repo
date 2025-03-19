package com.sparta.logistics.delivery_service.application.service.mock;
import com.sparta.logistics.delivery_service.application.dto.mock.ProductInfoDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockProductService {

    UUID productId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
    UUID departureHubId = UUID.fromString("123e4567-e89b-12d3-a456-426614174004");

    public ProductInfoDto getProductInfoDto() {
        return ProductInfoDto.builder()
                .productId(productId)
                .companyId(UUID.randomUUID())
                .hubId(departureHubId)
                .build();
    }

    public UUID getHubId(UUID productId) {
        return getProductInfoDto().getHubId();
    }
}
