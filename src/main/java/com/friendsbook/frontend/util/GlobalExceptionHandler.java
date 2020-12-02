package com.friendsbook.frontend.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.friendsbook.util.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ApiResponse> handleHttpException(HttpClientErrorException err){
		ApiResponse response = new ApiResponse(err.getResponseBodyAsString());
		return new ResponseEntity<ApiResponse>(response, err.getStatusCode());
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponse> handleHttpException(UnauthorizedException err){
		ApiResponse response = new ApiResponse(err.getMsg());
		return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
}
