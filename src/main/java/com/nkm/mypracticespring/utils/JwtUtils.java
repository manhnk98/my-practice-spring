package com.nkm.mypracticespring.utils;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.config.EnvConfig;
import com.nkm.mypracticespring.dto.jwt.CreateJwtDto;
import com.nkm.mypracticespring.dto.jwt.TokenGeneratedDto;
import com.nkm.mypracticespring.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class JwtUtils {

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(EnvConfig.JWT_SECRET.getBytes());
    }

    public static String generateToken(CreateJwtDto info, long expireTime, TokenType type) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + expireTime);
        Map<String, Object> claims = new HashMap<>();
        if (type.equals(TokenType.ACCESS_TOKEN)) {
            claims.put(Constant.PAYLOAD_USER_ID, info.userId());
            claims.put(Constant.PAYLOAD_EMAIL, info.email());
        } else if (type.equals(TokenType.REFRESH_TOKEN)) {
            claims.put(Constant.PAYLOAD_USER_ID, info.userId());
        }

        return Jwts.builder()
                .id(info.sessionId())
                .subject(type.name())
                .claims(claims)
                .issuedAt(now)
                .expiration(expired)
                .signWith(getSecretKey())
                .compact();
    }

    public static TokenGeneratedDto generateToken(CreateJwtDto info) {
        String accessToken = generateToken(info, Constant.ACCESS_TOKEN_EXPIRE_TIME, TokenType.ACCESS_TOKEN);
        String refreshToken = generateToken(info, Constant.REFRESH_TOKEN_EXPIRE_TIME, TokenType.REFRESH_TOKEN);
        return new TokenGeneratedDto(accessToken, refreshToken);
    }

    public static String getFromJwt(String token, String key) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return (String) claims.get(key);
    }

    public static boolean tokenIsValid(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty", ex);
        }
        return false;
    }

    public static void main(String[] args) {
        TokenGeneratedDto jwtToken = generateToken(new CreateJwtDto("manhnk", "nkm081198@gmail.com", "sessionId"));
        System.out.println("secretKey => " + getSecretKey().getAlgorithm());
        System.out.println("jwt token => " + jwtToken);
        System.out.println("validate token => " + tokenIsValid(jwtToken.accessToken()));
        System.out.println("username => " + getFromJwt(jwtToken.accessToken(), Constant.PAYLOAD_USER_ID));
    }

}

