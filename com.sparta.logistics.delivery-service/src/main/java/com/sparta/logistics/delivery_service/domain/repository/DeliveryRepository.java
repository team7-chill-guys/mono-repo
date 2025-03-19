package com.sparta.logistics.delivery_service.domain.repository;

import com.sparta.logistics.delivery_service.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {


    Optional<Delivery> findByIdAndDeletedAtIsNull(UUID deliveryId);

    List<Delivery> findByAndDeletedAtIsNull();
}
