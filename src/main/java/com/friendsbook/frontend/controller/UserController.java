package com.friendsbook.frontend.controller;

import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.service.UserServiceClient;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceClient client;
	
	Map<String, String> userServiceHeaders = new HashMap<String, String>();
	
	private HttpHeaders headers;
	
	@Value("${userservice.api.username}")
	private String userServiceUsername;
	@Value("${userservice.api.password}")
	private String userServicePassword;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private RestTemplate userServiceRestTemplate;
	
	@PostConstruct
	public void addHeaderProperties() {
		String auth = userServiceUsername + ":" + userServicePassword;
//		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
//		String authHeader = "Basic " + new String(encodedAuth);
		String authHeader = "Basic " + auth;
		userServiceHeaders.put("Authorization", authHeader);
		logger.info(authHeader);
		
		headers = new HttpHeaders();
		headers.setBasicAuth(userServiceUsername, userServicePassword);
		
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<String> createUser(@RequestBody User obj) throws SocketTimeoutException {
		return this.userServiceRestTemplate.exchange(
				"https://User-Microservice//user/sign-up",
				HttpMethod.POST,
				new HttpEntity<User>(obj, headers),
				String.class
				);
//		return this.client.createUser(userServiceHeaders,obj);
	}
	
	@GetMapping("/only-user")
	public String onlyUser() {
		return "Authenticated to be a user";
	}
}
