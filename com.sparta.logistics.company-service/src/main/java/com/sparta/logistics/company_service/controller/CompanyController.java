package com.sparta.logistics.company_service.controller;

import com.sparta.logistics.company_service.dto.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    // 임시 메모리 저장소
    private final Map<UUID, CompanyDetailResponseDto> companyDatabase = new HashMap<>();

    // [등록]
    @PostMapping
    public CompanyDetailResponseDto createCompany(@RequestBody CompanyCreateRequestDto requestDto) {
        UUID companyId = UUID.randomUUID();
        CompanyDetailResponseDto companyResponse = CompanyDetailResponseDto.builder()
                .id(companyId)
                .hubId(requestDto.getHubId())
                .name(requestDto.getName())
                .type(requestDto.getType())
                .address(requestDto.getAddress())
                .phone(requestDto.getPhone())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .createdBy(requestDto.getCreatedBy())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .updatedBy(requestDto.getUpdatedBy())
                .deletedAt(null)
                .deletedBy(null)
                .build();

        companyDatabase.put(companyId, companyResponse);
        return companyResponse;
    }

    // [수정]
    @PutMapping("/{id}")
    public CompanyDetailResponseDto updateCompany(@PathVariable UUID id, @RequestBody CompanyUpdateRequestDto updateDto) {
        CompanyDetailResponseDto existingCompany = companyDatabase.get(id);

        if (existingCompany == null) {
            throw new RuntimeException("Company not found");
        }

        if (existingCompany.getDeletedAt() != null) {
            throw new RuntimeException("This company has been deleted and cannot be modified.");
        }

        CompanyDetailResponseDto updatedCompany = CompanyDetailResponseDto.builder()
                .id(existingCompany.getId())
                .hubId(updateDto.getHubId())
                .name(updateDto.getName())
                .type(updateDto.getType())
                .address(updateDto.getAddress())
                .phone(updateDto.getPhone())
                .createdAt(existingCompany.getCreatedAt())
                .createdBy(existingCompany.getCreatedBy())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .updatedBy(updateDto.getUpdatedBy())
                .deletedAt(existingCompany.getDeletedAt())
                .deletedBy(existingCompany.getDeletedBy())
                .build();

        companyDatabase.put(id, updatedCompany);
        return updatedCompany;
    }

    // [삭제]
    @DeleteMapping("/{id}")
    public CompanyDeleteResponseDto deleteCompany(@PathVariable UUID id) {
        CompanyDetailResponseDto company = companyDatabase.get(id);

        if (company == null) {
            throw new RuntimeException("Company not found");
        }

        if (company.getDeletedAt() != null) {
            throw new RuntimeException("This company has already been deleted.");
        }

        company = CompanyDetailResponseDto.builder()
                .id(company.getId())
                .hubId(company.getHubId())
                .name(company.getName())
                .type(company.getType())
                .address(company.getAddress())
                .phone(company.getPhone())
                .createdAt(company.getCreatedAt())
                .createdBy(company.getCreatedBy())
                .updatedAt(company.getUpdatedAt())
                .updatedBy(company.getUpdatedBy())
                .deletedAt(new Timestamp(System.currentTimeMillis()))
                .deletedBy(company.getUpdatedBy())
                .build();

        companyDatabase.put(id, company);
        return new CompanyDeleteResponseDto(company.getId(), company.getDeletedAt(), company.getDeletedBy());
    }

    // [개별 조회]
    @GetMapping("/{id}")
    public CompanyDetailResponseDto getCompanyById(@PathVariable UUID id) {
        CompanyDetailResponseDto company = companyDatabase.get(id);

        if (company == null) {
            throw new RuntimeException("Company not found");
        }

        if (company.getDeletedAt() != null) {
            throw new RuntimeException("This company has already been deleted.");
        }

        return company;
    }

    // [전체 조회]
    @GetMapping
    public CompanyListResponseDto getAllCompanies() {
        List<CompanyDetailResponseDto> companyList = companyDatabase.values().stream()
                .filter(company -> company.getDeletedAt() == null)
                .toList();

        return CompanyListResponseDto.builder()
                .companies(companyList)
                .totalCount(companyList.size())
                .build();
    }
}
