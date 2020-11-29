package com.friendsbook.frontend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.friendsbook.frontend.model.User;
import com.friendsbook.util.ApiResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private RestTemplate http;
	
	@PostMapping(value = "/sign-up", consumes = "application/json")
	public ResponseEntity<ApiResponse> createUser(@RequestBody User obj) {
		HttpEntity<User> requestEntity = new HttpEntity<User>(obj);
		return this.http.exchange(
						"https://friendsbook-user-service.herokuapp.com/user/sign-up",
						HttpMethod.POST,
						requestEntity,
						ApiResponse.class);
		
	}

}
