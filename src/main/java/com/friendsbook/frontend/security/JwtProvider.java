package com.friendsbook.frontend.security;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.util.UnauthorizedException;
import com.friendsbook.functional.UsersRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	
	@Value("${jwt.secret:qwerttyoasdmakdmasd}")
	private String jwtKey;
	
	@Autowired
	private UsersRepository usRepo;
	
	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	public String generateToken(String email) {
		Date creation = new Date();
		Date expirationDate = Date.from(LocalDate.now().plusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant());// After 10 days token will expire
		return Jwts
					.builder()
					.setSubject(email)
					.setIssuedAt(creation)
					.setExpiration(expirationDate)
					.signWith(SignatureAlgorithm.HS256, jwtKey)
					.compact();	
	}
	
	public boolean valiateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
			
			// Check if token generated is created after password updated or not
			// so that token expires on password change/reset
			Date tokenCreationDate = getIssuedAtDate(token);
			User usr = this.usRepo.findByEmail(getUsernameFromToken(token));
			if(tokenCreationDate.before(usr.getLastPasswordUpdated()))
				return false;
			
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
	
	public Date getIssuedAtDate(String token) {
		return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody().getIssuedAt();
	}
}