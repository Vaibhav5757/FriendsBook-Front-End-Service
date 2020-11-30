package com.friendsbook.frontend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.friendsbook.frontend.model.UserDetails;
import com.friendsbook.frontend.service.UserDetailsService;

@Component
public class JWTFilter extends GenericFilter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4301299811872561102L;
	
	@Autowired
	private JwtProvider jwt;
	
	@Autowired
	private UserDetailsService usrSvc;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = this.getTokenFromRequest((HttpServletRequest) request);
		if(token != null && this.jwt.valiateToken(token)) {
			String username = this.jwt.getUsernameFromToken(token);// For our application, user email will be the login as well
			UserDetails usrDetails = this.usrSvc.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usrDetails, null, usrDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}
	
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if(bearer != null && bearer.startsWith("Bearer "))
			return bearer.substring(7);
		return null;
	}

}
