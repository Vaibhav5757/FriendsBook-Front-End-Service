package com.friendsbook.frontend.util;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationInitializer implements CommandLineRunner {
	
	@Value("${userservice.api.url}")
	private String userMicroserviceUrl;
	@Value("${userservice.api.username}")
	private String userServiceBasicAuthUsername;
	@Value("${userservice.api.password}")
	private String userServiceBasicAuthUserPassword;
	
	@Autowired
	private RestTemplate http;
	
	private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);
	
	private HttpHeaders headers;

	@Override
	public void run(String... args) throws Exception {
		
		String userSeviceAuthStr = userServiceBasicAuthUsername + ":" + userServiceBasicAuthUserPassword;
		String userSeviBase64Creds = Base64.getEncoder().encodeToString(userSeviceAuthStr.getBytes());
		headers = new HttpHeaders();
		headers.set("Authorization","Basic " + userSeviBase64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try {
			ResponseEntity<String> response = this.http.exchange(userMicroserviceUrl + "/wake-up/", HttpMethod.GET, new HttpEntity<Object>(null, headers), String.class);
			if(response.getBody().equals("Yo, I Woke up!!!")) {
				logger.info("User Microservice woke up");
			}else {
				logger.error("No response from user microservice");
			}
		}catch(Exception err) {
			logger.error(err.getMessage());
		}
		
	}

}
