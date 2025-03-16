package com.sparta.logistics.company_service.dto;

import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CompanyDeleteResponseDto {

    private UUID id;
    private Timestamp deletedAt;
    private Long deletedBy;
}
