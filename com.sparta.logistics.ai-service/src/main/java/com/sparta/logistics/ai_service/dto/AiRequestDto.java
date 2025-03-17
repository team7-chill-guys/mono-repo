package com.sparta.logistics.ai_service.dto;

import lombok.*;
import org.w3c.dom.Text;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiRequestDto {
    private String productName;
    private Long quantity;
    private Text request;
    private String departureHubName;
    private List<String> stopoverHubName;
    private String destinationHubName;
    private String workingHours;
}
