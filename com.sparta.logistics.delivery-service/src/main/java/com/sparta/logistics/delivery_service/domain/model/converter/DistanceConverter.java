package com.sparta.logistics.delivery_service.domain.model.converter;

import com.sparta.logistics.delivery_service.domain.model.Distance;
import com.sparta.logistics.delivery_service.domain.model.Time;
import jakarta.persistence.AttributeConverter;

public class DistanceConverter implements AttributeConverter<Distance, Double> {

    @Override
    public Double convertToDatabaseColumn(Distance distance) {
        return distance == null ? null : distance.getValue();
    }

    @Override
    public Distance convertToEntityAttribute(Double value) {
        return value == null ? null : new Distance(value);
    }
}
