package com.sparta.logistics.slack_service.infrastructure.config;

import com.sparta.logistics.slack_service.infrastructure.dto.DeliveryInfoDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerApplicationKafkaConfig {
    @Bean
    public ConsumerFactory<String, DeliveryInfoDto> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        configProps.put(JsonDeserializer.TYPE_MAPPINGS,
                "com.sparta.logistics.delivery_service.infrastructure.messaging.dto.DeliveryInfoDto:com.sparta.logistics.slack_service.infrastructure.dto.DeliveryInfoDto");

        configProps.put(JsonDeserializer.TRUSTED_PACKAGES,
                "com.sparta.logistics.delivery_service.infrastructure.messaging.dto.DeliveryInfoDto,com.sparta.logistics.slack_service.infrastructure.dto");

        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
                new JsonDeserializer<>(DeliveryInfoDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeliveryInfoDto>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DeliveryInfoDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
