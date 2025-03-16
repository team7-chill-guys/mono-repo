package com.sparta.logistics.company_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class CompanyUpdateRequestDto {

    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private long updatedBy;
}


