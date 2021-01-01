package com.friendsbook.frontend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.service.UserServiceClient;

import feign.FeignException;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UserServiceClientFallback implements UserServiceClient {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("unused")
	private Throwable cause;

	public UserServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public String createUser(User obj) {
		if(cause instanceof FeignException && ((FeignException) cause).status() == 400) {
			logger.error(cause.getMessage());
			return cause.getMessage().split(": ")[1].substring(1).replace("]", "");
		}
		return "Unable to create User";
	}

	@Override
	public String login(LoginBody obj) {
		if(cause instanceof FeignException && ((FeignException) cause).status() == 400) {
			logger.error(cause.getMessage());
			return cause.getMessage().split(": ")[1].substring(1).replace("]", "");
		}
		return "Unable to log in";
	}

	@Override
	public String updateToAdmin(JustEmailBody obj) {
		if(cause instanceof FeignException && ((FeignException) cause).status() == 400) {
			logger.error(cause.getMessage());
			return cause.getMessage().split(": ")[1].substring(1).replace("]", "");
		}
		return "Unable to update to admin";
	}

}
