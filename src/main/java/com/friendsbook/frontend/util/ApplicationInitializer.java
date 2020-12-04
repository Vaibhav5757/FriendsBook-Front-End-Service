//package com.friendsbook.frontend.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class ApplicationInitializer implements CommandLineRunner {
//	
//	@Value("${userservice.api.url}")
//	private String userMicroserviceUrl;
//	
//	@Autowired
//	private RestTemplate http;
//	
//	private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);
//
//	@Override
//	public void run(String... args) throws Exception {
//		
//		ResponseEntity<String> response = this.http.exchange(userMicroserviceUrl + "/wake-up", HttpMethod.GET, null, String.class);
//		if(response.getBody().equals("Yo, I Woke up!!!")) {
//			logger.info("User Microservice woke up");
//		}else {
//			logger.error("No response from user microservice");
//		}
//	}
//
//}
