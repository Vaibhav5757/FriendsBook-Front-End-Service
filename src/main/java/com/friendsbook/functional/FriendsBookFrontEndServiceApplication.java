package com.friendsbook.functional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.friendsbook.frontend.security.SecurityCenter;

@SpringBootApplication(exclude = SecurityCenter.class)
public class FriendsBookFrontEndServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsBookFrontEndServiceApplication.class, args);
	}

}
