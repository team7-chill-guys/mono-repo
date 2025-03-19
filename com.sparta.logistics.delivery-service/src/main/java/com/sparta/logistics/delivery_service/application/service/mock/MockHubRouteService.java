package com.sparta.logistics.delivery_service.application.service.mock;

import com.sparta.logistics.delivery_service.application.dto.mock.HubRouteInfoDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MockHubRouteService {

    UUID hubRouteId = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");

    UUID firstHubRouteId = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");
    UUID secondHubRouteId = UUID.fromString("123e4567-e89b-12d3-a456-426614174006");

    public HubRouteInfoDto getHubRouteInfoDto() {
        return HubRouteInfoDto.builder()
                .hubRouteId(hubRouteId)
                .startHubId(UUID.randomUUID())
                .endHubId(UUID.randomUUID())
                .estimatedTime(200)
                .estimatedDistance(50.457)
                .build();
    }

    public List<HubRouteInfoDto> getHubRoute(UUID departureHubId, UUID destinationHubId) {
        List<HubRouteInfoDto> hubRouteInfoList = new ArrayList<>();
        hubRouteInfoList.add(getHubRouteInfoDto());
        hubRouteInfoList.add(getHubRouteInfoDto());

        return hubRouteInfoList;
    }

    public Double getEstimatedDistance(UUID hubRouteId) {
        return getHubRouteInfoDto().getEstimatedDistance();
    }

    public Integer getEstimatedTime(UUID hubRouteId) {
        return getHubRouteInfoDto().getEstimatedTime();
    }

    public UUID getStartHubId(UUID routeId) {
        return UUID.randomUUID();
    }

    public UUID getEndHubId(UUID routeId) {
        return UUID.randomUUID();
    }
}
