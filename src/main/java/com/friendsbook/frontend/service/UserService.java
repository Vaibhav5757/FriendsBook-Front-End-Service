package com.friendsbook.frontend.service;

import java.net.URI;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.friendsbook.frontend.controller.UserController;
import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.security.JwtProvider;
import com.friendsbook.frontend.util.ApiResponse;
import com.friendsbook.frontend.util.LoginBody;

@Service
public class UserService {
	@SuppressWarnings("unused")
	private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	@Value("${userservice.api.username}")
	private String userServiceBasicAuthUsername;
	@Value("${userservice.api.password}")
	private String userServiceBasicAuthUserPassword;
	
	private String userSeviceAuthStr, userSeviBase64Creds;
	
	@Autowired
	private RestTemplate userSvcHttp;
	
	private HttpHeaders headers;
	
	@Autowired
	private JwtProvider jwt;
	
	@PostConstruct
	public void setHeaders() {
		userSeviceAuthStr = userServiceBasicAuthUsername + ":" + userServiceBasicAuthUserPassword;
		userSeviBase64Creds = Base64.getEncoder().encodeToString(userSeviceAuthStr.getBytes());
		headers = new HttpHeaders();
		headers.set("Authorization","Basic " + userSeviBase64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	// makes a HTTP Request to user service for creating a new User
	public ResponseEntity<ApiResponse> createUser(User obj) {
		HttpEntity<User> requestEntity = new HttpEntity<User>(obj, headers);
		URI uri = URI.create("https://User-Microservice/user/sign-up");
		ResponseEntity<String> response = this.userSvcHttp.exchange(
				uri,
				HttpMethod.POST,
				requestEntity,
				String.class);
		return new ResponseEntity<ApiResponse>(new ApiResponse(response.getBody()), response.getStatusCode());
	}
	
	// creates and returns the JWT token
	public ResponseEntity<ApiResponse> login(LoginBody obj){
		HttpEntity<LoginBody> requestEntity = new HttpEntity<LoginBody>(obj, headers);
		ResponseEntity<User> response =  this.userSvcHttp.exchange(
				"https://USER-MICROSERVICE/user/log-in",
				HttpMethod.POST,
				requestEntity,
				User.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			User target = response.getBody();
			return new ResponseEntity<ApiResponse>(new ApiResponse(this.jwt.generateToken(target.getEmail())), HttpStatus.OK);
		}else {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Invalid Credentials"), response.getStatusCode());
		}
	}
}
