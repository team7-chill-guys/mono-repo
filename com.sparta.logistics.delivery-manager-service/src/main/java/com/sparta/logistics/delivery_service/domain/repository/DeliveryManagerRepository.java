package com.sparta.logistics.delivery_service.domain.repository;

import com.sparta.logistics.delivery_service.domain.model.DeliveryManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, Long> {
    List<DeliveryManager> findByAndDeletedAtIsNull();

    Optional<DeliveryManager> findByIdAndDeletedAtIsNull(Long deliveryManagerId);

    @Query("SELECT COALESCE(MAX(d.sequence), 0) FROM DeliveryManager d")
    Integer getMaxSequence();
}
