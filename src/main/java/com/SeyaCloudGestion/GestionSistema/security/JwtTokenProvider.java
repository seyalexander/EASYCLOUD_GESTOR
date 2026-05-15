package com.SeyaCloudGestion.GestionSistema.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String idUsuario, String usuario, long idRol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idusuario", idUsuario);
        claims.put("nombreusuario", usuario);
        claims.put("idrol", idRol);

        return createToken(claims, idUsuario);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getNombreUsuarioFromToken(String token) {
        return (String) Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("nombreusuario");
    }

    public String getIdUsuarioFromToken(String token) {
        return (String) Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("idusuario");
    }

    public String getIdEmpresaFromToken(String token) {
        return (String) Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("idempresa");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            log.error("[JwtTokenProvider] JWT signature validation failed: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("[JwtTokenProvider] Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("[JwtTokenProvider] Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("[JwtTokenProvider] Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("[JwtTokenProvider] JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[JwtTokenProvider] Unexpected error validating token: {}", e.getMessage(), e);
        }
        return false;
    }

    public long getExpirationTime() {
        return jwtExpirationMs;
    }
}
