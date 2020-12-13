package com.friendsbook.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	private Logger logger = LoggerFactory.getLogger(RootController.class);

	@GetMapping("/wake-up")
	public String wakeUp() {
		logger.info("Wake up call recieved!!!");
		return "I am up and running :-D";
	}
}
