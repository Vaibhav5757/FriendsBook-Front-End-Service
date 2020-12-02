package com.friendsbook.frontend.service;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.friendsbook.frontend.controller.UserController;
import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.util.ApiResponse;

@Service
public class UserService {
	@SuppressWarnings("unused")
	private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	@Value("${userservice.api.username}")
	private String userServiceBasicAuthUsername;
	@Value("${userservice.api.password}")
	private String userServiceBasicAuthUserPassword;
	
	private String userSeviceAuthStr,userSeviBase64Creds;
	
	private RestTemplate userSvcHttp;
	
	private HttpHeaders headers;
	
	@PostConstruct
	public void setHeaders() {
		userSeviceAuthStr = userServiceBasicAuthUsername + ":" + userServiceBasicAuthUserPassword;
		userSeviBase64Creds = Base64.getEncoder().encodeToString(userSeviceAuthStr.getBytes());
		userSvcHttp = new RestTemplate();
		headers = new HttpHeaders();
		headers.set("Authorization","Basic " + userSeviBase64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	public ResponseEntity<ApiResponse> createUser(User obj) {
		HttpEntity<User> requestEntity = new HttpEntity<User>(obj, headers);
		ResponseEntity<String> response =  this.userSvcHttp.exchange(
						"https://friendsbook-user-service.herokuapp.com/user/sign-up",
						HttpMethod.POST,
						requestEntity,
						String.class);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(response.getBody()), response.getStatusCode());
	}
}
