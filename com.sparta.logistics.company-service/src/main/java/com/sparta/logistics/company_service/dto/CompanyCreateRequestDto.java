package com.sparta.logistics.company_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CompanyCreateRequestDto {
    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private long createdBy;
    private long updatedBy;

}
