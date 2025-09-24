package com.example.HotelBookingSystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtUtil {
    // Key >= 256 bits
    private static final String SECRET = "12345678901234567890123456789012";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Tạo JWT
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // ✅ dùng key chuẩn
                .compact();
    }

    // Xác thực JWT
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // ✅ không cần .getBytes()
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Lấy username từ JWT
    public static String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // ✅ không cần .getBytes()
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
