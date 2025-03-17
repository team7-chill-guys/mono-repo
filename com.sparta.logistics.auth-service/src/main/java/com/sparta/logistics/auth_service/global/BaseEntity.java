package com.sparta.logistics.auth_service.global;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @JoinColumn(name = "created_by")
    private UUID createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @JoinColumn(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JoinColumn(name = "deleted_by")
    private UUID deletedBy;

    // 생성을 위한 method 추가
    public BaseEntity(UUID authId) {
        this.createdBy = authId;
        this.updatedBy = authId;
    }

    // 소프트 delete를 위한 method 추가
    public void delete(UUID authId) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = authId;
    }

    // 업데이트를 위한 method 추가
    public void update(UUID authId) {
        this.updatedBy = authId;
    }
}