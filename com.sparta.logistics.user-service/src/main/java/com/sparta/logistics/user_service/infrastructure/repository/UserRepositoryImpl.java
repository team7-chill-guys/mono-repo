package com.sparta.logistics.user_service.infrastructure.repository;

import com.sparta.logistics.user_service.domain.entity.User;
import com.sparta.logistics.user_service.domain.repository.UserRepository;
import com.sparta.logistics.user_service.infrastructure.jpa.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

}
