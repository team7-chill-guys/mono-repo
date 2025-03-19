package com.sparta.logistics.delivery_service.domain.repository;

import com.sparta.logistics.delivery_service.domain.model.Delivery;
import com.sparta.logistics.delivery_service.domain.model.DeliveryRoute;
import com.sparta.logistics.delivery_service.domain.model.DeliveryRouteStatus;
import com.sparta.logistics.delivery_service.domain.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    List<DeliveryRoute> findByDeliveryIdAndDeletedAtIsNull(UUID deliveryId);

    Optional<DeliveryRoute> findByIdAndDeliveryIdAndDeletedAtIsNull(UUID routesId, UUID deliveryId);

    List<DeliveryRoute> findByStatusAndDeletedAtIsNull(DeliveryRouteStatus deliveryStatus);
}
