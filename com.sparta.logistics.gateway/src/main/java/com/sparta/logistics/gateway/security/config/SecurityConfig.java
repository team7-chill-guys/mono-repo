package com.sparta.logistics.gateway.security.config;

import com.sparta.logistics.gateway.jwt.JwtUtil;
import com.sparta.logistics.gateway.security.filter.PreAuthWebFilter;
import com.sparta.logistics.gateway.security.service.BlackListCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final BlackListCheck blackListCheck;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http
        ) {

        /*
        PreAuthWebFilter 가 두번씩 동작하는 현상 발생. PreAuthWebFilter 의 컴포넌트 어노테이션을 제거한 후
        시큐리티 웹 필터체인 안에 직접 생성하여 사용. -> 한번만 사용됨.
         */
        PreAuthWebFilter preAuthWebFilter = new PreAuthWebFilter(jwtUtil, blackListCheck);

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
            )
            .addFilterBefore(preAuthWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

}

