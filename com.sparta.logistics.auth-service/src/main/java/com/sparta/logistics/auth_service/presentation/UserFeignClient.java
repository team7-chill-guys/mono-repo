package com.sparta.logistics.auth_service.presentation;

import com.sparta.logistics.auth_service.application.dto.request.UserSignupRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @PostMapping("/api/users/signup")
    void createUser(UserSignupRequestDto userSignupRequestDto);

}
