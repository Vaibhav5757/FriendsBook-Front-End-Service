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
	
	@Value("userservice.api.url")
	String userSeviceUrl;
	
	@Value("{eureka.client.service-url.defaultZone")
	String eurekaServerUrl;
	
	@Autowired
	private RestTemplate http;
	
	private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

	@Override
	public void run(String... args) throws Exception {
		
		logger.info("here");
		
//		// Wake up Eureka Server
//		ResponseEntity<String> eurekaServerResponse = this.http.exchange(eurekaServerUrl, HttpMethod.GET, null, String.class);
//		if(!eurekaServerResponse.getBody().isEmpty()) {
//			logger.info("Eureka recieved the wake up call");
//		}
//		
//		// Wake up User Service
//		ResponseEntity<String> userServiceResponse = this.http.exchange(userSeviceUrl + "/wake-up", HttpMethod.GET, null, String.class);
//		if(!userServiceResponse.getBody().isEmpty()) {
//			logger.info("User Microservice recieved the wake up call");
//		}
	}

}
