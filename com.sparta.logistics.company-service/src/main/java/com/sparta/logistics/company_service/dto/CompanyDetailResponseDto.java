package com.sparta.logistics.company_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class CompanyDetailResponseDto {
    private UUID id;
    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private Timestamp createdAt;
    private long createdBy;
    private Timestamp updatedAt;
    private long updatedBy;
    private Timestamp deletedAt;
    private Long deletedBy;
}
