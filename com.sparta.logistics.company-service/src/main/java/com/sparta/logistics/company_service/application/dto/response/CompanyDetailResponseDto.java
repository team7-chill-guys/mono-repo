package com.sparta.logistics.company_service.application.dto.response;

import com.sparta.logistics.company_service.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailResponseDto {
    private UUID id;
    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;

    public static CompanyDetailResponseDto fromEntity(Company company) {
        return CompanyDetailResponseDto.builder()
                .id(company.getId())
                .hubId(company.getHubId().getHubId())
                .name(company.getName())
                .type(company.getType().name())
                .address(company.getAddress())
                .phone(company.getPhone())
                .build();
    }
}
