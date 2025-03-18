package com.sparta.logistics.gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // JWT 토큰 사용으로 무상태 요청을 유지하기 위한 설정.
        http
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        http
            .csrf(CsrfSpec::disable)
            .formLogin(FormLoginSpec::disable)
            .httpBasic(HttpBasicSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                // actuator 경로 허옹
                .pathMatchers("/actuator/gateway/routes").permitAll()
                // 회원가입, 로그인 경로 허용
                .pathMatchers("/api/auth/signup", "/api/auth/login").permitAll()
                .anyExchange().authenticated()
            );

        return http.build();
    }

}

