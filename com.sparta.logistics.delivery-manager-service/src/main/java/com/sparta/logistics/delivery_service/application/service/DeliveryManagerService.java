package com.sparta.logistics.delivery_service.application.service;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerCreateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerUpdateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryManagerResponseDto;
import com.sparta.logistics.delivery_service.application.mapper.DeliveryManagerMapper;
import com.sparta.logistics.delivery_service.domain.model.DeliveryManager;
import com.sparta.logistics.delivery_service.domain.repository.DeliveryManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryManagerService {

    private final DeliveryManagerRepository deliveryManagerRepository;

    @Transactional
    public void createDeliveryManager(DeliveryManagerCreateRequestDto deliveryManagerCreateRequestDto) {
        // User에서 id 값 받아오기
        Long id = 555L;

        Integer sequence = deliveryManagerRepository.getMaxSequence() + 1;

        DeliveryManager deliveryManager = DeliveryManagerMapper.toEntity(deliveryManagerCreateRequestDto, id, sequence);
        deliveryManagerRepository.save(deliveryManager);
    }

    @Transactional(readOnly = true)
    public List<DeliveryManagerResponseDto> getDeliveryManagerList() {
        List<DeliveryManager> deliveryManagerList = deliveryManagerRepository.findByAndDeletedAtIsNull();
        List<DeliveryManagerResponseDto> deliveryManagerResponseDtoList = new ArrayList<>();

        for(DeliveryManager deliveryManager : deliveryManagerList) {
            deliveryManagerResponseDtoList.add(DeliveryManagerMapper.toDto(deliveryManager));
        }

        return deliveryManagerResponseDtoList;
    }

    @Transactional(readOnly = true)
    public DeliveryManagerResponseDto getDeliveryManager(Long deliveryManagerId) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findByIdAndDeletedAtIsNull(deliveryManagerId)
                .orElseThrow(() -> new RuntimeException("배송담당자 없음"));
        return DeliveryManagerMapper.toDto(deliveryManager);
    }

    @Transactional
    public void updateDeliveryManager(Long deliveryManagerId, DeliveryManagerUpdateRequestDto deliveryManagerUpdateRequestDto) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findByIdAndDeletedAtIsNull(deliveryManagerId)
                .orElseThrow(() -> new RuntimeException("배송담당자 없음"));

        deliveryManager.updateOf(deliveryManagerUpdateRequestDto);
        deliveryManagerRepository.save(deliveryManager);
    }

    @Transactional
    public void deleteDeliveryManager(Long deliveryManagerId) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findByIdAndDeletedAtIsNull(deliveryManagerId)
                .orElseThrow(() -> new RuntimeException("배송담당자 없음"));

        deliveryManager.deletedOf();
    }
}
