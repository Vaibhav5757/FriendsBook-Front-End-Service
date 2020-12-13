package com.friendsbook.functional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.friendsbook"})
@EntityScan(basePackages="com.friendsbook")
@EnableFeignClients
public class FriendsBookFrontEndServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsBookFrontEndServiceApplication.class, args);
	}
	
}
