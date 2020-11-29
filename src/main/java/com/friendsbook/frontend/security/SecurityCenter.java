package com.friendsbook.frontend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityCenter extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.httpBasic().disable() // disable basic http
			.formLogin().disable() // disable login page
			.logout().disable() // disable logout
			.csrf().disable() // disable cross site request forgery
			.cors().disable() // disable cors
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()// authorize requests
			.antMatchers("/user/*").hasRole("USER")
			.antMatchers("/admin/*").hasRole("ADMIN")
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	// allow these url(s) to be public
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers("/user/sign-up");
	}
}
