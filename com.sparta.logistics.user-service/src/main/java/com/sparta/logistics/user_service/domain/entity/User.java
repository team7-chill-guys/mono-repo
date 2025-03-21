package com.sparta.logistics.user_service.domain.entity;

import com.sparta.logistics.user_service.application.dto.request.AuthSignupRequestDto;
import com.sparta.logistics.user_service.application.dto.request.UserPasswordUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.request.UserRoleUpdateRequestDto;
import com.sparta.logistics.user_service.application.dto.request.UserUpdateRequestDto;
import com.sparta.logistics.user_service.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static User fromRequest(AuthSignupRequestDto requestDto) {
        return User.builder()
            .username(requestDto.getUsername())
            .password(BCrypt.hashpw(requestDto.getPassword(), BCrypt.gensalt()))
            .slackId(requestDto.getSlackId())
            .role(UserRole.ROLE_USER)
            .build();
    }

    public void updatePassword(UserPasswordUpdateRequestDto requestDto) {
        this.password = BCrypt.hashpw(requestDto.getNewPassword(), BCrypt.gensalt());
    }

    public void updateRole(UserRoleUpdateRequestDto requestDto) {
        if (requestDto.getNewRole() != null && !requestDto.getNewRole().isEmpty()) {
            try {
                this.role = UserRole.valueOf(requestDto.getNewRole());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("권한 변경에 잘못된 ROLE 값이 들어왔습니다. 받아온 ROLE : " + requestDto.getNewRole());
            }
        }
    }

    public void updateUser(UserUpdateRequestDto requestDto) {
        if (requestDto.getNewUsername() != null && !requestDto.getNewUsername().isEmpty()) {
            this.username = requestDto.getNewUsername();
        }
        if (requestDto.getNewSlackId() != null && !requestDto.getNewSlackId().isEmpty()) {
            this.slackId = requestDto.getNewSlackId();
        }
    }

}
