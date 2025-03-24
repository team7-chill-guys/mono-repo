package com.sparta.logistics.hub_service.hub.domain.repository;

import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

  boolean existsByUserId(Long userId);

  boolean existsByHubName(String hubName);

  boolean existsByAddress(String address);

  Page<Hub> findByHubNameContainingOrAddressContaining(String hubName, String address, Pageable pageable);
}
