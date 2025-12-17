package com.example.GreatEvents.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String SECRET = "mySecretKey";
    private static final long EXPIRATION = 864000000L; // 10 días

    public String generateToken(String subject) {
        return "Bearer " + JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(Algorithm.HMAC512(SECRET));
    }

    public boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SECRET)).build();
            verifier.verify(token.replace("Bearer ", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token.replace("Bearer ", ""));
        return jwt.getSubject();
    }
}
