package com.friendsbook.frontend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationInitializer implements CommandLineRunner{
	
	@Value("${USER-SERVICE-URL}")
	String userSeviceUrl; 
	
	@Autowired
	private RestTemplate http;
	
	private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

	@Override
	public void run(String... args) throws Exception {
		// Wake up User Service
		ResponseEntity<String> response = this.http.exchange(userSeviceUrl, HttpMethod.GET, null, String.class);
		if(!response.getBody().isEmpty()) {
			logger.info("User Microservice recieved the wake up call");
		}
	}

}
