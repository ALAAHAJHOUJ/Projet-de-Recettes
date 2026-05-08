package com.example.demo.securite;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "bm90cmVjbGVzZWNyZXRlcG91cmxldHBmc2FjMjAyNiE=";
    // ============================================
    // BLOC 1 : CRÉER le token
    // ============================================
    public String generateToken(String username,String role) {
        return Jwts.builder()
                .setSubject(username) // QUI ? (le username)
                .claim("role",role)
                .setIssuedAt(new Date()) // QUAND ? (maintenant)
                .setExpiration(new Date( // JUSQU'À QUAND ? (10h)
                        System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey()) // SIGNATURE (la clé secrète)
                .compact();
    }
    // ============================================
    // BLOC 2 : LIRE le token
    // ============================================
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token,
                claims -> claims.get("role", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }
    // ============================================
    // BLOC 3 : VÉRIFIER le token
    // ============================================

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Vérifier que le token n'est pas expiré
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("=== JWT INVALID === " + e.getMessage());
            return false;
        }
    }
    // ============================================
    // UTILITAIRE : la clé de signature
    // ============================================
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
