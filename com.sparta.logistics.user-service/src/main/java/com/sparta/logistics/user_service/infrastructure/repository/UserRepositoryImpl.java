package com.sparta.logistics.user_service.infrastructure.repository;

import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.entity.UserRole;
import com.sparta.logistics.user_service.domain.repository.UserRepository;
import com.sparta.logistics.user_service.infrastructure.jpa.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
       return userJpaRepository.findByUsername(username);
    }

    @Override
    public Page<User> findByRole(UserRole roleEnum, Pageable pageable) {
        return userJpaRepository.findByRole(roleEnum, pageable);
    }

    @Override
    public Page<User> findByAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

}
