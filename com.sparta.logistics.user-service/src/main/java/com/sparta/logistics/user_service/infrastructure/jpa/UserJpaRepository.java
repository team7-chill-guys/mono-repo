package com.sparta.logistics.user_service.infrastructure.jpa;

import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.entity.UserRole;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Page<User> findByRole(UserRole role, Pageable pageable);
}
