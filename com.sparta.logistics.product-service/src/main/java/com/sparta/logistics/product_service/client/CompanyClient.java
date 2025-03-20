package com.sparta.logistics.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-service")
public interface CompanyClient {
    @GetMapping("/api/compaines/{companyId}")
    UUID getCompany(@PathVariable("companyId") UUID companyId);
}
