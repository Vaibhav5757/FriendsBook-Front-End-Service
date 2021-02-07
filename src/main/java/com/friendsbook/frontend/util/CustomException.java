package com.friendsbook.frontend.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5262043468226942380L;
	
	private HttpStatus statusCode;
	private String msg;

}
