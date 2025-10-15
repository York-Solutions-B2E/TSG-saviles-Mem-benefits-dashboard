package org.mbd.bememberbenefitsdashboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mbd.bememberbenefitsdashboard.entity.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    // In production, store this securely (e.g. environment variable)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 1 hour expiration for demo
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getAuthSub()) // Google sub (unique ID)
                .claim("email", user.getEmail())
                .claim("provider", user.getAuthProvider())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String extractSubject(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public static Key getSecretKey() {
        return SECRET_KEY;
    }

}

