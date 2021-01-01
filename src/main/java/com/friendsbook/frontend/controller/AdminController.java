package com.friendsbook.frontend.controller;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friendsbook.frontend.service.UserServiceClient;
import com.friendsbook.frontend.util.ApiResponse;
import com.friendsbook.frontend.util.JustEmailBody;
import com.friendsbook.frontend.util.PasswordChangeBodyAdmin;
import com.friendsbook.frontend.util.PasswordChangeBodyForUserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserServiceClient client;

	@GetMapping("/authority-check")
	public ResponseEntity<ApiResponse> authorityCheck() throws SocketTimeoutException{
		ApiResponse response = new ApiResponse("You are authorized as a admin");
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	
	@PutMapping("/update-to-admin")
	public ResponseEntity<ApiResponse> updateToAdmin(@RequestBody JustEmailBody obj) throws SocketTimeoutException{
		ApiResponse response = new ApiResponse(this.client.updateToAdmin(obj));
		if(response.getMsg().equals("User upgraded to admin privileges"))// if response was success
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
		else return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<ApiResponse> changePassword(@RequestBody PasswordChangeBodyAdmin obj) throws SocketTimeoutException{
		PasswordChangeBodyForUserService temp = new PasswordChangeBodyForUserService(obj.getEmail(),obj.getPassword());
		ApiResponse response = new ApiResponse(this.client.changePassword(temp)); 
		if(response.getMsg().equals("Password Changed Successfully"))// if response was success
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
		else return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}
}
