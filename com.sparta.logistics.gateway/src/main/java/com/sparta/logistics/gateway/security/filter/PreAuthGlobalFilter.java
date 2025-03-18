package com.sparta.logistics.gateway.security.filter;

import com.sparta.logistics.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "PreAuthGlobalFilter")
public class PreAuthGlobalFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;
    private static final String SECURITY_CONTEXT_ATTRIBUTES_KEY = "team7-chill-guys";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Authorization 헤더 추출
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // 토큰이 없다면 다음 필터로 이동
        if (authHeader == null) {
            return chain.filter(exchange);
        }

        // 토큰 검증
        String token = jwtUtil.removeBearer(authHeader);
        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            log.error("유효하지 않은 토큰입니다.");
            return exchange.getResponse().setComplete();
        }

        // 토큰의 클레임에서 데이터 추출
        Claims claims = jwtUtil.getClaims(token);
        String userId = claims.getSubject();
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);

        UsernamePasswordAuthenticationToken authToke = new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );

        authToke.setDetails(userId);

        SecurityContext securityContext = new SecurityContextImpl(authToke);
        ServerWebExchange passportExchange = exchange.mutate()
            .request(r -> r.headers(headers -> {
                headers.add("X-User-Id", userId);
                headers.add("X-User-Name", username);
                headers.add("X-User-Role", role);
            }))
            .build();

        return chain.filter(passportExchange)
            .contextWrite(ctx -> ctx.put(SECURITY_CONTEXT_ATTRIBUTES_KEY, securityContext));

    }

}
