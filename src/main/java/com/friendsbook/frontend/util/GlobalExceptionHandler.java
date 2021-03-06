package com.friendsbook.frontend.util;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
	
	// Following is to handle exceptions for hibernate validator
	// For more info, you can refer this article - https://www.baeldung.com/spring-boot-bean-validation
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    StringBuilder sb = new StringBuilder();
	    errors.forEach((key, value) -> {
	    	sb.append(key + " " + value + ". ");
	    });
	    return new ResponseEntity<ApiResponse>(new ApiResponse(sb.toString()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SocketTimeoutException.class)
	public ResponseEntity<ApiResponse> handleHttpException(SocketTimeoutException err){
		logger.error(err.getMessage());
		ApiResponse response = new ApiResponse("Connection Timed out to external service");
		return new ResponseEntity<ApiResponse>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse> handleCustomException(CustomException ex){
		logger.error(ex.getMsg());
		ApiResponse response = new ApiResponse(ex.getMsg());
		return new ResponseEntity<ApiResponse>(response, ex.getStatusCode());
	}
	
	
}
