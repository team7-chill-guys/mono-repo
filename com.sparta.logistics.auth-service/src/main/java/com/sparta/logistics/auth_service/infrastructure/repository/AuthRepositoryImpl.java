package com.sparta.logistics.auth_service.infrastructure.repository;

import com.sparta.logistics.auth_service.domain.entity.Auth;
import com.sparta.logistics.auth_service.domain.repository.AuthRepository;
import com.sparta.logistics.auth_service.infrastructure.jpa.AuthJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

    private final AuthJpaRepository authJpaRepository;

    @Override
    public Optional<Auth> findById(UUID id) {
        return authJpaRepository.findById(id);
    }

    @Override
    public Auth save(Auth auth) {
        return authJpaRepository.save(auth);
    }

}
