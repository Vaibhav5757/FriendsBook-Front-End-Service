package com.friendsbook.frontend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.friendsbook.frontend.service.UserServiceClient;

import feign.hystrix.FallbackFactory;

@Component
public class UserServiceClientFallbackFactory implements  FallbackFactory<UserServiceClient>{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserServiceClient create(Throwable cause) {
		logger.error(cause.getMessage());
		return new UserServiceClientFallback(cause);
	}
}
