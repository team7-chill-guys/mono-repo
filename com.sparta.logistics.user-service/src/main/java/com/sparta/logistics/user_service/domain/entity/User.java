package com.sparta.logistics.user_service.domain.entity;

import com.sparta.logistics.user_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.user_service.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "slack_id", unique = true)
    private String slackId;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UserRole role;

    public static User fromRequest(AuthSignupRequestDto requestDto, String password) {
        return User.builder()
            .username(requestDto.getUsername())
            .password(password)
            .slackId(requestDto.getSlackId())
            .role(UserRole.ROLE_USER)
            .build();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
