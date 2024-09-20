package com.nkm.mypracticespring.security.jwt;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.config.EnvConfig;
import com.nkm.mypracticespring.dto.access.ShopInfo;
import com.nkm.mypracticespring.dto.jwt.CreateJwtDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(EnvConfig.JWT_SECRET.getBytes());
    }

    public static String generateToken(CreateJwtDto info, long expireTime) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + expireTime);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.PAYLOAD_USER_ID, info.getUserId());
        claims.put(Constant.PAYLOAD_EMAIL, info.getEmail());
        return Jwts.builder()
                .subject("userInfo")
                .claims(claims)
                .issuedAt(now)
                .expiration(expired)
                .signWith(getSecretKey())
                .compact();
    }

    public static String generateToken(Map<String, Object> params, long expireTime) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + expireTime);
        return Jwts.builder()
                .subject("userInfo")
                .claims(params)
                .issuedAt(now)
                .expiration(expired)
                .signWith(getSecretKey())
                .compact();
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

    public static boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    public static void main(String[] args) {
        String jwtToken = generateToken(new CreateJwtDto("manhnk", "nkm081198@gmail.com"), Constant.TOKEN_EXPIRE_TIME);
        System.out.println("secretKey => " + getSecretKey().getAlgorithm());
        System.out.println("jwt token => " + jwtToken);
        System.out.println("validate token => " + validateToken(jwtToken));
        System.out.println("username => " + getFromJwt(jwtToken, Constant.PAYLOAD_USER_ID));
    }

}

