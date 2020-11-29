package com.friendsbook.frontend.security;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	
	@Value("${jwt.secret}")
	private String jwtKey;

	public String generateToken(String email) {
		Date expirationDate = Date.from(LocalDate.now().plusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant());// After 10 days token will expire
		return Jwts
					.builder()
					.setSubject(email)
					.setExpiration(expirationDate)
					.signWith(SignatureAlgorithm.HS256, jwtKey)
					.compact();	
	}
	
	public boolean valiateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
			return true;
		}catch (Exception ex) {
			
		}
		return false;
	}
	
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
