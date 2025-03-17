package com.sparta.logistics.hub_service.hub.domain.repository;

import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

}
