package com.sparta.logistics.auth_service.domain.repository;

import com.sparta.logistics.auth_service.domain.entity.Auth;
import java.util.Optional;
import java.util.UUID;

public interface AuthRepository {

    Optional<Auth> findById(UUID id);
    Auth save(Auth auth);

}
