package com.sparta.logistics.company_service.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanyResponseDto {
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