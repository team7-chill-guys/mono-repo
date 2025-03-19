package com.sparta.logistics.user_service.infrastructure.jpa;

import com.sparta.logistics.user_service.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
