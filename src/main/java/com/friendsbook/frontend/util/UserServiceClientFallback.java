package com.friendsbook.frontend.util;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.service.UserServiceClient;

public class UserServiceClientFallback implements UserServiceClient {

	@Override
	public String createUser(User obj) {
		return "Unable to create User";
	}

}
