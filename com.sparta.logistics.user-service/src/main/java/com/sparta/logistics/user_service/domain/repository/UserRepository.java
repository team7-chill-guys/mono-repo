package com.sparta.logistics.user_service.domain.repository;

import com.sparta.logistics.user_service.domain.entity.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface UserRepository {

    Optional<User> findById(Long id);

    void save(User user);
}
