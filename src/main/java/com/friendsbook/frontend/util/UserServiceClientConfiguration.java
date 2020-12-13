package com.friendsbook.frontend.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;


public class UserServiceClientConfiguration {
	
	@Value("${userservice.api.username}")
	public String userServiceAuthenticationUsername;
	@Value("${userservice.api.password}")
	public String userServiceAuthenticationPassword;
		
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
	    return new BasicAuthRequestInterceptor(userServiceAuthenticationUsername, userServiceAuthenticationPassword);
	}
	
	@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
