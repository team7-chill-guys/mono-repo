package com.sparta.logistics.user_service.domain.repository;

import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.entity.UserRole;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    void save(User user);

    Optional<User> findByUsername(String username);

    List<User> findByRole(UserRole roleEnum);
}
