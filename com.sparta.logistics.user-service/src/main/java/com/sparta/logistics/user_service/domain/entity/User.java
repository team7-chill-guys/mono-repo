package com.sparta.logistics.user_service.domain.entity;

import com.sparta.logistics.user_service.application.dto.request.UserSignupRequestDto;
import com.sparta.logistics.user_service.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
public class User extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "auth_id")
    private UUID authId;

    @Column(name = "username")
    private String username;

    @Column(name = "slack_id")
    private String slackId;

    public static User fromRequest(UserSignupRequestDto requestDto) {
        return User.builder()
            .username(requestDto.getUsername())
            .authId(requestDto.getAuthId())
            .slackId(requestDto.getSlackId())
            .build();
    }

}
