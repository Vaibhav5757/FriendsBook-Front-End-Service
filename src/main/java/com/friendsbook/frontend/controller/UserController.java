package com.friendsbook.frontend.controller;

import java.net.SocketTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.security.JwtProvider;
import com.friendsbook.frontend.service.UserServiceClient;
import com.friendsbook.frontend.util.ApiResponse;
import com.friendsbook.frontend.util.LoginBody;
import com.friendsbook.frontend.util.PasswordChangeBody;
import com.friendsbook.frontend.util.PasswordChangeBodyForUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceClient client;
	
	@SuppressWarnings("unused")
	@Autowired
	private JwtProvider jwt;
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse> createUser(@RequestBody User obj) throws SocketTimeoutException {
		ApiResponse response = new ApiResponse(this.client.createUser(obj));
		if(response.getMsg().equals("User Created Successfully"))// if response was success
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
		else return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginBody obj) throws SocketTimeoutException {
		String target = this.client.login(obj);
		
		try {
			User usr = new ObjectMapper().readValue(target, User.class);//Convert JSON User String to POJO Json Object
			String token = this.jwt.generateToken(usr.getEmail());// generate token
			ApiResponse response = new ApiResponse("Token is " + token);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
			
		} catch (JsonProcessingException err) { //Handle Bad Request Scenarios
			logger.error(err.getMessage());
			ApiResponse response = new ApiResponse(target);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/authority-check")
	public ResponseEntity<ApiResponse> authorityCheck() throws SocketTimeoutException{
		ApiResponse response = new ApiResponse("You are authorized as a user");
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<ApiResponse> changePassword(@RequestBody PasswordChangeBody obj, @RequestHeader("Authorization") String token) throws SocketTimeoutException {
		PasswordChangeBodyForUserService temp = new PasswordChangeBodyForUserService(
					this.jwt.getUsernameFromToken(token.substring(7)),
					obj.getPassword());
		ApiResponse response = new ApiResponse(this.client.changePassword(temp));
		if(response.getMsg().equals("Password Changed Successfully"))// if response was success
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
		else return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
}
