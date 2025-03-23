package com.sparta.logistics.slack_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Slack {
    @Id
    private UUID id;
    private String text;
    private Timestamp createdAt;
    private Long createdBy;
}
