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
public class CompanyDeleteDto {
    private UUID id;
    private Timestamp deletedAt;
    private Long deletedBy;
}
