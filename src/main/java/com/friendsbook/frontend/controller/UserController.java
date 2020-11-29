package com.friendsbook.frontend.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${USER_SERVICE_USERNAME}")
	private String userServiceBasicAuthUsername;
	@Value("${USER_SERVICE_PASSWORD}")
	private String userServiceBasicAuthUserPassword;
	
	String userSeviceAuthStr = userServiceBasicAuthUsername + ":" + userServiceBasicAuthUserPassword;
	String userSeviBase64Creds = Base64.getEncoder().encodeToString(userSeviceAuthStr.getBytes());
	
	@PostMapping(value = "/sign-up", consumes = "application/json")
	public ResponseEntity<ApiResponse> createUser(@RequestBody User obj) {
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set("Authorization", "Basic " + userSeviBase64Creds);
		
		HttpEntity<User> requestEntity = new HttpEntity<User>(obj, headers);
		return this.http.exchange(
						"https://friendsbook-user-service.herokuapp.com/user/sign-up",
						HttpMethod.POST,
						requestEntity,
						ApiResponse.class);
		
	}

}
