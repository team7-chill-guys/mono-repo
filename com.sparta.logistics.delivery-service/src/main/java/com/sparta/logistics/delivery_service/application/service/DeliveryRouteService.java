package com.sparta.logistics.delivery_service.application.service;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryRouteUpdateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryRouteResponseDto;
import com.sparta.logistics.delivery_service.application.mapper.DeliveryRouteMapper;
import com.sparta.logistics.delivery_service.application.service.mock.MockDeliveryManagerService;
import com.sparta.logistics.delivery_service.application.service.mock.MockHubRouteService;
import com.sparta.logistics.delivery_service.domain.model.Delivery;
import com.sparta.logistics.delivery_service.domain.model.DeliveryRoute;
import com.sparta.logistics.delivery_service.domain.model.DeliveryRouteStatus;
import com.sparta.logistics.delivery_service.domain.model.DeliveryStatus;
import com.sparta.logistics.delivery_service.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sparta.logistics.delivery_service.domain.model.QDelivery.delivery;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

    private final DeliveryRouteRepository deliveryRouteRepository;
    private final MockHubRouteService mockHubRouteService;
    private final MockDeliveryManagerService mockDeliveryManagerService;

    public void createDeliveryRoutes(Delivery delivery) {

        // 1. 출발+도착 허브 보고 허브간 이동경로 조회
        List<UUID> hubRouteId = mockHubRouteService.getHubRoute(delivery.getDepartureHubId(), delivery.getDestinationHubId());

        // 2. 조회 된 허브간 이동 경로에서 시작허브, 도착허브 예상거리, 예상 소요시간 조회
        Integer sequence = 1;

        for(UUID routeId : hubRouteId){
            UUID startHubId = mockHubRouteService.getStartHubId(routeId);
            UUID endHubId = mockHubRouteService.getEndHubId(routeId);
            Double estimatedDistance = mockHubRouteService.getEstimatedDistance(routeId);
            Integer estimatedTime = mockHubRouteService.getEstimatedTime(routeId);

            Double actualDistance = 212.4677;
            Integer actualTime = 135;

            DeliveryRoute deliveryRoute = DeliveryRouteMapper.toEntity(
                    delivery.getId(),
                    sequence++,
                    startHubId,
                    endHubId,
                    estimatedDistance,
                    estimatedTime,
                    actualDistance,
                    actualTime);

            deliveryRouteRepository.save(deliveryRoute);
        }
    }

    public List<DeliveryRouteResponseDto> getDeliveryRoutesList(UUID deliveryId) {
        List<DeliveryRoute> deliveryRouteList = deliveryRouteRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId);

        List<DeliveryRouteResponseDto> deliveryRouteResponseDtoList = new ArrayList<>();

        for(DeliveryRoute deliveryRoute : deliveryRouteList){
            deliveryRouteResponseDtoList.add(DeliveryRouteMapper.toDto(deliveryRoute));
        }

        return deliveryRouteResponseDtoList;
    }

    public DeliveryRouteResponseDto getDeliveryRoute(UUID deliveryId, UUID routesId) {
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryIdAndDeletedAtIsNull(routesId, deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 기록 없음"));

        return DeliveryRouteMapper.toDto(deliveryRoute);
    }

    @Transactional
    public void updateDeliveryRoute(UUID deliveryId, UUID routesId, DeliveryRouteUpdateRequestDto deliveryRouteUpdateRequestDto) {
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryIdAndDeletedAtIsNull(routesId, deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 기록 없음"));

        if(deliveryRoute.isInfoChangeable()) {
            deliveryRoute.updateOf(deliveryRouteUpdateRequestDto);
        } else throw new RuntimeException("배송 중이라 변경 못함");

        deliveryRouteRepository.save(deliveryRoute);
    }


    @Transactional
    public void deleteDeliveryRoute(UUID deliveryId, UUID routesId) {
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryIdAndDeletedAtIsNull(routesId, deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 기록 없음"));

        deliveryRoute.deletedOf();
    }

    @Transactional
    public void assignHudDeliveryManager(UUID deliveryId, UUID routesId) {
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryIdAndDeletedAtIsNull(routesId, deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 기록 없음"));

        UUID departureHubId = deliveryRoute.getStartHudId();
        Long hudDeliveryManagerId = mockDeliveryManagerService.getDeliveryManager(departureHubId, "HUB");

        deliveryRoute.assignHubDeliveryManager(hudDeliveryManagerId);

        deliveryRouteRepository.save(deliveryRoute);
    }

    @Transactional
    public void assignPendingDeliveries() {
        List<DeliveryRoute> pendingDeliveryies = deliveryRouteRepository.findByStatusAndDeletedAtIsNull(DeliveryRouteStatus.PENDING);

        if(!pendingDeliveryies.isEmpty()) {
            for(DeliveryRoute deliveryRoute : pendingDeliveryies) {
                UUID startHubId = deliveryRoute.getStartHudId();
                Long hubDeliveryManagerId = mockDeliveryManagerService.getDeliveryManager(startHubId, "HUB");
                deliveryRoute.assignHubDeliveryManager(hubDeliveryManagerId);

                deliveryRouteRepository.save(deliveryRoute);
                log.info("HubDeliveryManager Assigned");
            }
        }
    }

    @Transactional
    public void changeDeliveryStatus(UUID deliveryId, UUID routesId, DeliveryRouteStatus status) {
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryIdAndDeletedAtIsNull(routesId, deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 기록 없음"));
        deliveryRoute.changeDeliveryStatus(status);
        deliveryRouteRepository.save(deliveryRoute);
    }
}
