package com.sparta.logistics.auth_service.infrastructure.jpa;

import com.sparta.logistics.auth_service.domain.entity.Auth;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthJpaRepository extends JpaRepository<Auth, UUID> {

}
