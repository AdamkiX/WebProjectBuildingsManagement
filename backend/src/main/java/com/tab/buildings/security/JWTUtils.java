package com.tab.buildings.security;

import com.tab.buildings.rep.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    @Autowired
    private UserRepository userRepository;

    private SecretKey Key;
    private static final long EXPIRATION_TIME = 15 * 60 * 1000; //15mins
    private static final long REFRESH_EXPIRATION_TIME = 86400000; //24hours or 86400000 milisecs

    public JWTUtils() {
        String secreteString = "Nevergonnagiveyouup" +
                "Nevergonnaletyoudown" +
                "Nevergonnarunaroundanddesertyou" +
                "Nevergonnamakeyoucry" +
                "Nevergonnasaygoodbye" +
                "Nevergonnatellalieandhurtyou";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /**
     * Generate an access token
     *
     * @param userDetails - User details object
     * @return - JWT access token
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    /**
     * Generate a refresh token
     *
     * @param claims - Claims to be added to the token
     * @param userDetails - User details object
     * @return - JWT refresh token
     */
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    /**
     * Extract username from token
     *
     * @param token - JWT token
     * @return - Username
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extract claims from token
     *
     * @param token - JWT token
     * @param claimsTFunction - Function to extract claims
     * @param <T> - Type of claims
     * @return - Claims
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    /**
     * Validate token
     *
     * @param token - JWT token
     * @param userDetails - User details object
     * @return - Boolean value indicating if the token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Check if token is expired
     *
     * @param token - JWT token
     * @return - Boolean value indicating if the token is expired
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}