package com.sparta.logistics.company_service.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanySearchDto {
    private List<CompanyResponseDto> companies;
    private int totalCount;
    private int page;
    private int size;
}
