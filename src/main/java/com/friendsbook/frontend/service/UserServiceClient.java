package com.friendsbook.frontend.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.util.FollowRequestBody;
import com.friendsbook.frontend.util.JustEmailBody;
import com.friendsbook.frontend.util.LoginBody;
import com.friendsbook.frontend.util.PasswordChangeBodyForUserService;
import com.friendsbook.frontend.util.UserServiceClientConfiguration;
import com.friendsbook.frontend.util.UserServiceClientFallbackFactory;

@FeignClient(
	value = "http://user-microservice/",
	fallbackFactory = UserServiceClientFallbackFactory.class,
	configuration = UserServiceClientConfiguration.class)
public interface UserServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/user/sign-up", consumes = "application/json")
	public String createUser(User obj);
	
	@RequestMapping(method = RequestMethod.POST, value = "/user/log-in", consumes = "application/json")
	public String login(LoginBody obj);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/admin/update-to-admin", consumes = "application/json")
	public String updateToAdmin(JustEmailBody obj);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/user/change-password", consumes = "application/json")
	public String changePassword(PasswordChangeBodyForUserService obj);
	
	@RequestMapping(method = RequestMethod.POST, value = "/user/follow", consumes = "application/json")
	public String follow(FollowRequestBody obj);
	
	
}