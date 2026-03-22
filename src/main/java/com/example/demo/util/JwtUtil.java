package com.example.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

//	    @Value("${app.jwt.secret}")
//	    private String SECRET;
//
//	    @Value("${app.jwt.accessTokenExpirationMs}")
//	    private Long ACCESS_TOKEN_VALIDITY;

	private final Key key;

	public JwtUtil(@Value("${jwt.secret}") String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(String phoneNumber) {
		return Jwts.builder().setSubject(phoneNumber).signWith(key, SignatureAlgorithm.HS256).compact();
	}

//	    public String extractUserId(String token) {
//	        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
//	    }
//
//	    public boolean isTokenValid(String token) {
//	        try {
//	            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
//	            return true;
//	        } catch (Exception e) {
//	            return false;
//	        }
//	    }

	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token);

	        return true;

	    } catch (ExpiredJwtException e) {
	        System.out.println("Token expired");
	    } catch (JwtException e) {
	        System.out.println("Invalid token: " + e.getMessage());
	    }

	    return false;
	}



}
