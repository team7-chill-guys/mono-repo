package com.sparta.logistics.delivery_service.presentation;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerCreateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerUpdateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryManagerInfoDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryManagerResponseDto;
import com.sparta.logistics.delivery_service.application.service.DeliveryManagerService;
import com.sparta.logistics.delivery_service.domain.model.DeliveryManagerType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerController {

    private final DeliveryManagerService deliveryManagerService;

    /**
     * 배송 담당자 생성
     */
    @PostMapping
    public ResponseEntity<Void> createDeliveryManager(@RequestBody DeliveryManagerCreateRequestDto deliveryManagerCreateRequestDto) {
        deliveryManagerService.createDeliveryManager(deliveryManagerCreateRequestDto);
        return ResponseEntity.ok().build();
    }


    /**
     * 배송 담당자 조회
     */
    @GetMapping
    public ResponseEntity<List<DeliveryManagerResponseDto>> getDeliveryManagerList() {
        return ResponseEntity.ok().body(deliveryManagerService.getDeliveryManagerList());
    }

    /**
     * 배송 담당자 단건 조회
     */
    @GetMapping("/{delivery_manager_id}")
    public ResponseEntity<DeliveryManagerResponseDto> getDeliveryManager(@PathVariable("delivery_manager_id") Long deliveryManagerId) {
        return ResponseEntity.ok().body(deliveryManagerService.getDeliveryManager(deliveryManagerId));
    }

    /**
     * 배송 담당자 수정
     */
    @PutMapping("/{delivery_manager_id}")
    public ResponseEntity<Void> updateDeliveryManager(@PathVariable("delivery_manager_id") Long deliveryManagerId,
                                                      @RequestBody DeliveryManagerUpdateRequestDto deliveryManagerUpdateRequestDto) {
        deliveryManagerService.updateDeliveryManager(deliveryManagerId, deliveryManagerUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 배송 담당자 삭제
     */
    @DeleteMapping("/{delivery_manager_id}")
    public ResponseEntity<Void> deleteDeliveryManager(@PathVariable("delivery_manager_id") Long deliveryManagerId) {
        deliveryManagerService.deleteDeliveryManager(deliveryManagerId);
        return ResponseEntity.ok().build();
    }


    /**
     * 배송 담당자 배정
     */
    @GetMapping("/assign")
    public ResponseEntity<DeliveryManagerInfoDto> assignDeliveryManager(@RequestParam UUID startHubId,
                                                                        @RequestParam UUID endHubId,
                                                                        @RequestParam DeliveryManagerType type) {
        return ResponseEntity.ok().body(deliveryManagerService.assignDeliveryManager(startHubId, endHubId, type));
    }
}
