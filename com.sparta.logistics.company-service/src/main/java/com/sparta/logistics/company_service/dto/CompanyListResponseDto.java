package com.sparta.logistics.company_service.dto;

import lombok.*;
import java.util.List;

@Getter
@Builder
public class CompanyListResponseDto {
    private List<CompanyDetailResponseDto> companies;
    private int totalCount;
}
