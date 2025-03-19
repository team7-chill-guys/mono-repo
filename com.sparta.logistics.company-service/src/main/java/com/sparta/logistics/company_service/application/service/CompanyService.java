package com.sparta.logistics.company_service.application.service;

import com.sparta.logistics.company_service.application.dto.request.CompanyCreateRequestDto;
import com.sparta.logistics.company_service.application.dto.request.CompanyUpdateRequestDto;
import com.sparta.logistics.company_service.application.dto.response.CompanyDetailResponseDto;
import com.sparta.logistics.company_service.domain.Company;
import com.sparta.logistics.company_service.domain.HubId;
import com.sparta.logistics.company_service.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    // [생성]
    @Transactional
    public CompanyDetailResponseDto createCompany(CompanyCreateRequestDto requestDto) {
        Company company = Company.create(requestDto, new HubId(requestDto.getHubId())); // ✅ 팩토리 메서드 사용
        companyRepository.save(company);
        return CompanyDetailResponseDto.fromEntity(company);
    }

    // [개별 조회]
    @Transactional(readOnly = true)
    public CompanyDetailResponseDto getCompanyById(UUID id) {
        return companyRepository.findByIdAndDeletedAtIsNull(id)
                .map(CompanyDetailResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    // [전체 조회]
    @Transactional(readOnly = true)
    public Page<CompanyDetailResponseDto> getAllCompanies(Pageable pageable) {
        return companyRepository.findAllByDeletedAtIsNull(pageable)
                .map(CompanyDetailResponseDto::fromEntity);
    }

    // [수정]
    @Transactional
    public CompanyDetailResponseDto updateCompany(UUID id, CompanyUpdateRequestDto updateDto, Long updatedBy) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.updateCompany(updateDto, updatedBy);
        return CompanyDetailResponseDto.fromEntity(company);
    }

    // [삭제]
    @Transactional
    public void deleteCompany(UUID id, Long deletedBy) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.deleteCompany(deletedBy);
    }
}
