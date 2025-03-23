package com.sparta.logistics.user_service.domain.repository;

import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.entity.UserRole;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

    Optional<User> findById(Long id);

    void save(User user);

    Optional<User> findByUsername(String username);

    Page<User> findByRole(UserRole roleEnum, Pageable pageable);
}
