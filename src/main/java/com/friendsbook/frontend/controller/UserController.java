package com.friendsbook.frontend.controller;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.service.UserServiceClient;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceClient client;
	
	@PostMapping("/sign-up")
	public String createUser(@RequestBody User obj) throws SocketTimeoutException {
		return this.client.createUser(obj);
	}
	
	@GetMapping("/only-user")
	public String onlyUser() {
		return "Authenticated to be a user";
	}
}
