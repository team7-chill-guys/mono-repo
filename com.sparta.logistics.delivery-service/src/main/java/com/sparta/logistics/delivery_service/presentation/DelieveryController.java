package com.sparta.logistics.delivery_service.presentation;

import com.sparta.logistics.delivery_service.application.dto.request.OrderDeliveryRequestDto;
import com.sparta.logistics.delivery_service.application.dto.request.DeliveryUpdateRequestDto;
import com.sparta.logistics.delivery_service.application.dto.response.DeliveryResponseDto;
import com.sparta.logistics.delivery_service.application.dto.response.PageResponseDto;
import com.sparta.logistics.delivery_service.application.service.DeliveryService;
import com.sparta.logistics.delivery_service.domain.model.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DelieveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 & 배송 경로 생성
     */
    @PostMapping
    public ResponseEntity<UUID> createDelivery(@RequestBody OrderDeliveryRequestDto orderDeliveryRequestDto) {
        return ResponseEntity.ok().body(deliveryService.createDelivery(orderDeliveryRequestDto));
    }

    /**
     * 배송 조회
     */
    @GetMapping
    public ResponseEntity<List<DeliveryResponseDto>> getDeliveryList() {
        return ResponseEntity.ok().body(deliveryService.getDeliveryList());
    }

    /**
     * 배송 단건 조회
     */
    @GetMapping("/{delivery_id}")
    public ResponseEntity<DeliveryResponseDto> getDelivery(@PathVariable("delivery_id") UUID deliveryId) {
        return ResponseEntity.ok().body(deliveryService.getDelivery(deliveryId));
    }

    /**
     * 배송 수정
     */
    @PutMapping("/{delivery_id}")
    public ResponseEntity<Void> updateDelivery(@PathVariable("delivery_id") UUID deliveryId,
                                               @RequestBody DeliveryUpdateRequestDto updateDeliveryUpdateRequestDto) {
        deliveryService.updateDelivery(deliveryId, updateDeliveryUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 배송 상태 변경
     */
    @PutMapping("/{delivery_id}/status")
    public ResponseEntity<Void> changeDeliveryStatus(@PathVariable("delivery_id") UUID deliveryId,
                                                     @RequestParam("status")DeliveryStatus status) {
        deliveryService.changeDeliveryStatus(deliveryId, status);
        return ResponseEntity.ok().build();
    }

    /**
     * 배송 삭제
     */
    @DeleteMapping("/{delivery_id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable("delivery_id") UUID deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    /**
     * 배송 검색
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<DeliveryResponseDto>> searchDelivery(
            @RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok().body(new PageResponseDto<>());
    }

}
