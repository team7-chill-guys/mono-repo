package com.sparta.logistics.user_service.presentation.controller;

import com.sparta.logistics.user_service.application.dto.request.AuthSignupRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", url = "http://localhost:19092/")
public interface UserFeignClient {

    @PostMapping("/api/users/signup")
    void createUser(AuthSignupRequestDto userSignupRequestDto);

}
