package com.friendsbook.frontend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitializer implements CommandLineRunner {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

	@Override
	public void run(String... args) throws Exception {
		
	}

}
