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
public class CompanyRequestDto {
    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private long createdBy;
    private long updatedBy;

}
