package com.sparta.logistics.product_service.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseProductUpdateDto {
    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
    private Timestamp updatedAt;
    private Long updatedBy;
}
