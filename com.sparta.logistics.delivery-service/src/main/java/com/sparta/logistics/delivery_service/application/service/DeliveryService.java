package com.sparta.logistics.delivery_service.application.service;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryCreateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.request.DeliveryUpdateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryResponseDto;
import com.sparta.logistics.delivery_service.application.mapper.DeliveryMapper;
import com.sparta.logistics.delivery_service.application.service.mock.MockDeliveryManager;
import com.sparta.logistics.delivery_service.application.service.mock.MockOrderService;
import com.sparta.logistics.delivery_service.application.service.mock.MockProductService;
import com.sparta.logistics.delivery_service.domain.model.Delivery;
import com.sparta.logistics.delivery_service.domain.model.DeliveryStatus;
import com.sparta.logistics.delivery_service.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteService deliveryRouteService;

    private final MockOrderService mockOrderService;
    private final MockProductService mockProductService;
    private final MockDeliveryManager mockDeliveryManager;

    public void createDelivery(DeliveryCreateRequestDto deliveryCreateRequestDto) {
        // 1. 상품을 보고 출발 허브 결정
        UUID orderId = deliveryCreateRequestDto.getOrderId();
        UUID productId = mockOrderService.getProductId(orderId);
        UUID departureHubId = mockProductService.getHubId(productId);

        // 2. 수령 주소보고 도착 허브 결정
        UUID destinationHubId = mockOrderService.getReceiverId(orderId);

        // 3. 배송 저장
        Delivery delivery = DeliveryMapper.toEntity(deliveryCreateRequestDto, departureHubId, destinationHubId);
        deliveryRepository.save(delivery);

        deliveryRouteService.createDeliveryRoutes(delivery);

    }

    public List<DeliveryResponseDto> getDeliveryList() {
        List<Delivery> deliveryList = deliveryRepository.findByAndDeletedAtIsNull();

        List<DeliveryResponseDto> deliveryResponseDtoList = new ArrayList<>();
        for(Delivery delivery : deliveryList) {
            deliveryResponseDtoList.add(DeliveryMapper.toDto(delivery));
        }
        return deliveryResponseDtoList;
    }

    public DeliveryResponseDto getDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
                .orElseThrow(() -> new RuntimeException());
        return DeliveryMapper.toDto(delivery);
    }

    @Transactional
    public void updateDelivery(UUID deliveryId, DeliveryUpdateRequestDto deliveryUpdateRequestDto) {
        Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 찾을 수 없음"));

        if(delivery.isInfoChangeable()) {
            delivery.updateOf(deliveryUpdateRequestDto);
        } else throw new RuntimeException("배송중이라 변경 못함");

        deliveryRepository.save(delivery);
    }

    @Transactional
    public void deleteDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 찾을 수 없음"));

        delivery.deletedOf();
    }

    @Transactional
    public void assignDeliveryManager(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 찾을 수 없음"));

        UUID departureHubId = delivery.getDepartureHubId();

        Long deliveryManagerId = mockDeliveryManager.getDeliveryManager(departureHubId);

        delivery.assignDeliveryManager(deliveryManagerId);

        // TODO : 슬랙에 이벤트 전송

        deliveryRepository.save(delivery);
    }

    @Transactional
    public void changeDeliveryStatus(UUID deliveryId, DeliveryStatus deliveryStatus) {
        Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 찾을 수 없음"));

        delivery.changeDeliveryStatus(deliveryStatus);
        deliveryRepository.save(delivery);
    }
}
