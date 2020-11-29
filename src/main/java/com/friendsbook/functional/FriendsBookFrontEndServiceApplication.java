package com.friendsbook.functional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaServer
@SpringBootApplication
@ComponentScan
public class FriendsBookFrontEndServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsBookFrontEndServiceApplication.class, args);
	}

}
