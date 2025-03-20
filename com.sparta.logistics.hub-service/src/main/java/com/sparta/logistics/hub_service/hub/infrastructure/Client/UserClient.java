package com.sparta.logistics.hub_service.hub.infrastructure.Client;

import com.sparta.logistics.hub_service.hub.application.dto.response.UserRoleSearchResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service")
public interface UserClient {
  @GetMapping("/users/role")
  List<UserRoleSearchResponseDto> roleSearchUser(@RequestParam("userRole") String userRole);
}
