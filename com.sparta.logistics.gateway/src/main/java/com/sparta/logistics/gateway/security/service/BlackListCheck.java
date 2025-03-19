package com.sparta.logistics.gateway.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListCheck {

    private final StringRedisTemplate stringRedisTemplate;

    public boolean isBlacklisted(String token) {
        String key = "blacklist" + token;
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

}
