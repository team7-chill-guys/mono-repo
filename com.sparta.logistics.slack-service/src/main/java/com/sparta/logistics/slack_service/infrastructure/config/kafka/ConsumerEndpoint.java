package com.sparta.logistics.slack_service.infrastructure.config.kafka;

import com.sparta.logistics.slack_service.infrastructure.dto.DeliveryInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerEndpoint {

    @KafkaListener(groupId = "hub", topics = "HUB")
    public void hubListen(DeliveryInfoDto dto) {
        log.info("카프카에서 받아온 HUB 메세지: " + dto.toString());
    }

    @KafkaListener(groupId = "company", topics = "COMPANY")
    public void listen(DeliveryInfoDto dto) {
        log.info("카프카에서 받아온 COMPANY 메세지: " + dto.toString());
    }
}
