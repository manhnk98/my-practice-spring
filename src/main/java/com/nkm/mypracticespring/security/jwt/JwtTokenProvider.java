package com.nkm.mypracticespring.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    private static final String JWT_SECRET = "5pAq6zRyX8bC3dV2wS7gN1mK9jF0hL4tUoP6iBvE3nG8xZaQrY7cW2fA";
    private static final long JWT_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
    private static final String USERNAME_KEY = "username";

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    public static String generateToken(String username) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + JWT_EXPIRE_TIME);
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME_KEY, username);
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(now)
                .expiration(expired)
                .signWith(getSecretKey())
                .compact();
    }

    public static String getUserNameFromJwt(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return (String) claims.get(USERNAME_KEY);
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
        String jwtToken = generateToken("manhnk");
        System.out.println("secretKey => " + getSecretKey().getAlgorithm());
        System.out.println("jwt token => " + jwtToken);
        System.out.println("validate token => " + validateToken(jwtToken));
        System.out.println("username => " + getUserNameFromJwt(jwtToken));
    }

}

