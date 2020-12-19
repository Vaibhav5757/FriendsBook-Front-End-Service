package com.friendsbook.frontend.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.util.UserServiceClientConfiguration;
import com.friendsbook.frontend.util.UserServiceClientFallback;

@FeignClient(
	value = "https://User-Microservice/",
	fallback = UserServiceClientFallback.class,
	configuration = UserServiceClientConfiguration.class)
public interface UserServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/user/sign-up", consumes = "application/json")
	public String createUser(@RequestHeader Map<String, String> headers,User obj);
	
}