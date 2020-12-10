package com.friendsbook.frontend.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.friendsbook.frontend.model.User;

@FeignClient(name = "User-Service", url = "https://User-Microservice/")
public interface UserServiceClient {
	
	@PostMapping("/user/sign-up/")
	public String createUser(@RequestHeader("Authorization") String authHeader, User obj);
}
