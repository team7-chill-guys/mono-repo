package com.sparta.logistics.hub_service.global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  private Key key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
  }

  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String getUsername(String token) {
    return getUserInfoFromToken(token).get("username", String.class);
  }

  public String getRole(String token) {
    return getUserInfoFromToken(token).get("role", String.class);
  }

  public String getUserId(String token) {
    return getUserInfoFromToken(token).getSubject(); // sub 값
  }
}


