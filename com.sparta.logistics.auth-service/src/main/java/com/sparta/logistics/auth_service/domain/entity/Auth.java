package com.sparta.logistics.auth_service.domain.entity;

import com.sparta.logistics.auth_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.auth_service.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_auth")
public class Auth extends BaseEntity {

    @Id
    @UuidGenerator
    public UUID authId;

    public String username;
    public String password;

    @Column(name = "role", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AuthRole role;

    public void updatePassword(String password) {
        this.password = password;
    }

    public static Auth fromRequest(AuthSignupRequestDto requestDto, String password) {
        return Auth.builder()
            .username(requestDto.getUsername())
            .password(password)
            .role(AuthRole.ROLE_USER)
            .build();
    }

}
