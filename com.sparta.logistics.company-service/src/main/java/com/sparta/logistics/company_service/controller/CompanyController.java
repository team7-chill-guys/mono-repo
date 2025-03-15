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
    private final Map<UUID, CompanyResponseDto> companyDatabase = new HashMap<>();

    // [등록]
    @PostMapping
    public CompanyResponseDto createCompany(@RequestBody CompanyRequestDto requestDto) {
        UUID companyId = UUID.randomUUID();
        CompanyResponseDto companyResponse = CompanyResponseDto.builder()
                .id(companyId)
                .hubId(requestDto.getHubId())
                .name(requestDto.getName())
                .type(requestDto.getType())
                .address(requestDto.getAddress())
                .phone(requestDto.getPhone())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
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
    public CompanyResponseDto updateCompany(@PathVariable UUID id, @RequestBody CompanyUpdateDto updateDto) {
        CompanyResponseDto existingCompany = companyDatabase.get(id);

        if (existingCompany == null) {
            throw new RuntimeException("Company not found");
        }

        if (existingCompany.getDeletedAt() != null) {
            throw new RuntimeException("This company has been deleted and cannot be modified.");
        }

        existingCompany.setHubId(updateDto.getHubId());
        existingCompany.setName(updateDto.getName());
        existingCompany.setType(updateDto.getType());
        existingCompany.setAddress(updateDto.getAddress());
        existingCompany.setPhone(updateDto.getPhone());
        existingCompany.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        existingCompany.setUpdatedBy(updateDto.getUpdatedBy());

        return existingCompany;
    }

    // [삭제]
    @DeleteMapping("/{id}")
    public CompanyDeleteDto deleteCompany(@PathVariable UUID id) {
        CompanyResponseDto company = companyDatabase.get(id);

        if (company == null) {
            throw new RuntimeException("Company not found");
        }

        if (company.getDeletedAt() != null) {
            throw new RuntimeException("This company has already been deleted.");
        }

        company.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        company.setDeletedBy(company.getUpdatedBy()); // updatedBy를 기준으로 설정

        return new CompanyDeleteDto(company.getId(), company.getDeletedAt(), company.getDeletedBy());
    }

    // [개별 조회]
    @GetMapping("/{id}")
    public CompanyResponseDto getCompanyById(@PathVariable UUID id) {
        CompanyResponseDto company = companyDatabase.get(id);

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
    public CompanySearchDto getAllCompanies(
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        // 삭제된 데이터 제외
        List<CompanyResponseDto> companyList = companyDatabase.values().stream()
                .filter(company -> company.getDeletedAt() == null) // ✅ 삭제된 데이터 제외
                .collect(Collectors.toList());

        // 정렬 처리
        if ("desc".equalsIgnoreCase(sortOrder)) {
            companyList.sort(Comparator.comparing(CompanyResponseDto::getName).reversed());
        } else {
            companyList.sort(Comparator.comparing(CompanyResponseDto::getName));
        }

        // 페이징 처리
        int start = Math.min(page * size, companyList.size());
        int end = Math.min(start + size, companyList.size());
        List<CompanyResponseDto> paginatedList = companyList.subList(start, end);

        // 응답 DTO 생성
        return CompanySearchDto.builder()
                .companies(paginatedList)
                .totalCount(companyList.size())
                .page(page)
                .size(size)
                .build();
    }
}
