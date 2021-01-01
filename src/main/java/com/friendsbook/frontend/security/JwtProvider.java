package com.friendsbook.frontend.security;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.friendsbook.frontend.util.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	
	@Value("${jwt.secret}")
	private String jwtKey;
	
	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

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
		}catch (ExpiredJwtException err) {
			logger.error(err.getMessage());
			new UnauthorizedException("Token Expired. Please login again");
		}catch(UnsupportedJwtException | MalformedJwtException | SignatureException err ) {
			logger.error(err.getMessage());
			new UnauthorizedException("Invalid Token. Please sign in to get valid token");
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return false;
	}
	
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
