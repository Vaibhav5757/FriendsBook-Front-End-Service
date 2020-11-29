package com.friendsbook.frontend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
			.formLogin().disable()
			.logout().disable();
		
		http
			.httpBasic().disable()
			.csrf().disable()// disable cross site request forgery
			.cors().disable()// disable cors
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()// authorize requests
			.antMatchers("/user/sign-up").permitAll()// allow these url(s) to be public
			.antMatchers("/user/*").hasRole("USER")
			.antMatchers("/admin/*").hasRole("ADMIN")
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
