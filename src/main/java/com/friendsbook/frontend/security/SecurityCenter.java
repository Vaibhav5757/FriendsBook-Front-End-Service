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
			.httpBasic().disable() // disable http basic authentication type
			.csrf().disable()// disable cross site request forgery
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()// authorize requests
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/sign-up").permitAll() // make these url(s) public
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
