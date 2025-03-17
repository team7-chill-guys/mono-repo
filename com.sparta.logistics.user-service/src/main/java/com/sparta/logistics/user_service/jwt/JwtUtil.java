package com.sparta.logistics.user_service.jwt;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "JwtUil")
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.access.exp}")
    private Long jwtAccessExpTime;

    @Value("${jwt.refresh.exp}")
    private Long jwtRefreshExpTime;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    // Access 토큰 생성
    public String createAccessToken(Long userId, String role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtAccessExpTime);

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    public String createRefreshToken(Long userId, String role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtRefreshExpTime);

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    // 헤더에서 토큰 추출
    public String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        return removeBearer(bearerToken);
    }

    // 헤더에서 추출한 토큰의 Bearer 접두사 제거
    public String removeBearer(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        log.error("bearer 접두사를 찾을 수 없거나 토큰이 존재하지 않음. 받은 토큰 : " + bearerToken);
        return null;
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty");
        }
        return false;
    }

    // 토큰에서 user 데이터 가져오기
    public Claims getUserInfoFromToken(String token) {
        // 토큰 파싱하여 리턴
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }


}
