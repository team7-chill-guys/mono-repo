package com.sparta.logistics.slack_service.repository;

import com.sparta.logistics.slack_service.entity.Slack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SlackRepository extends JpaRepository<Slack, UUID> {
}
