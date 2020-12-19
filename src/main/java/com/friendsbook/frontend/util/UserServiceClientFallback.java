package com.friendsbook.frontend.util;

import java.util.Map;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.service.UserServiceClient;

public class UserServiceClientFallback implements UserServiceClient {

	@Override
	public String createUser(Map<String, String> headers,User obj) {
		return "Unable to create User";
	}

}
