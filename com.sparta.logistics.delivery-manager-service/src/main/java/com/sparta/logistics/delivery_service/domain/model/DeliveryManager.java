package com.sparta.logistics.delivery_service.domain.model;

import com.sparta.logistics.delivery_service.application.dto.request.DeliveryManagerUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_delivery_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManager extends BaseTimeEntity{

    @Id
    private Long id;

    @Column(nullable = false)
    private UUID hubId;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false)
    private DeliveryManagerType type;

    @Column(nullable = false)
    private Integer sequence;

    @Builder
    private DeliveryManager(Long id, UUID hubId, String slackId, DeliveryManagerType type, Integer sequence) {
        this.id = id;
        this.hubId = hubId;
        this.slackId = slackId;
        this.type = type;
        this.sequence = sequence;
    }

    public void updateOf(DeliveryManagerUpdateRequestDto deliveryManagerUpdateRequestDto) {
        this.hubId = deliveryManagerUpdateRequestDto.getHubId();
        this.slackId = deliveryManagerUpdateRequestDto.getSlackId();
        this.type = deliveryManagerUpdateRequestDto.getType();
        this.sequence = deliveryManagerUpdateRequestDto.getSequence();
    }
}
