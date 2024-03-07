package com.shop.croquy.v1.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IJwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    @Value("${token.signing.duration}")
    private Integer jwtSigningDuration;

    @Override
    public String extractUsernameFormToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails, boolean everlasting) {
        return tokenBuilder(new HashMap<>(), userDetails, everlasting);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, boolean everlasting) {
        final String userName = extractUsernameFormToken(token);
        return userName.equals(userDetails.getUsername()) && (everlasting || !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String tokenBuilder(Map<String, Object> extraClaims, UserDetails userDetails, boolean everlasting) {
        JwtBuilder builder = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);

        if(everlasting) {
            builder.setExpiration(new Date(System.currentTimeMillis() + jwtSigningDuration));
        }

        return builder.compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
