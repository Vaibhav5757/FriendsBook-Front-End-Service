package com.friendsbook.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.service.UserService;
import com.friendsbook.frontend.util.ApiResponse;
import com.friendsbook.frontend.util.LoginBody;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService usrSvc;

	@PostMapping(value = "/sign-up")
	public ResponseEntity<ApiResponse> createUser(@RequestBody User obj){
		return this.usrSvc.createUser(obj);
	}
	
	@PostMapping(value = "/log-in", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginBody obj){
		return this.usrSvc.login(obj);
	}
}
