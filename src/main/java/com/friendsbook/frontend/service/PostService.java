package com.friendsbook.frontend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;

import javax.annotation.PostConstruct;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.friendsbook.frontend.model.Posts;
import com.friendsbook.frontend.security.JwtProvider;
import com.friendsbook.frontend.util.ApiResponse;
import com.friendsbook.frontend.util.CustomException;

import lombok.extern.java.Log;

@Service
@Log
public class PostService {
	
	@Autowired
	private RestTemplate http;
	
	@Value("${postservice.api.username}")
	private String postSvcUsername;
	@Value("${postservice.api.password}")
	private String postSvcPassword;
	
	@Autowired
	private JwtProvider jwt;
	
	private HttpHeaders headers;
	
	
	@PostConstruct
	public void setBasicAuthHeaders() {
		// Set auth to basic header for post service
		String plainCreds = postSvcUsername + ":" + postSvcPassword;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Authorization", "Basic " + base64Creds);
	}

	public ResponseEntity<ApiResponse> post(String token, MultipartFile file, String text) throws IOException, CustomException{
		
		// Check if file is present or not
		if(file.getSize() == 0 && (text == "" || text == null || text.equals(""))) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Post cannot be empty - Either upload image or post some text");
		}
		
		// Check if file extension is correct or not
		if(file.getSize() != 0) {
			String[] split = file.getOriginalFilename().split("\\.");
		    String ext = split[split.length - 1];
		    String[] validExtensions = {"jpg","png","jpeg"};
		    if(!Arrays.asList(validExtensions).contains(ext)) {
		    	throw new CustomException(HttpStatus.BAD_REQUEST, "File Type Invalid. Please use a image type file");
		    }
		}
	    
		// Get username from token
		String username = this.jwt.getUsernameFromToken(token.substring(7));
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("owner", username);
		map.add("file", compressBytes(file.getBytes()));
		map.add("text", text);
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response =  this.http.postForEntity("http://post-service/post", entity, String.class);
		ApiResponse resp = new ApiResponse(response.getBody());
		
		return new ResponseEntity<ApiResponse>(resp,response.getStatusCode());
	}
	
	
	public Posts[] getPosts(String author) {
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("owner", author);
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
		
		ResponseEntity<Posts[]> temp = this.http.postForEntity("http://post-service/getPosts", entity, Posts[].class);
		log.info(temp.getBody().toString());
		return temp.getBody();
		
	}
	
	public byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		
		try {
			outputStream.close();
		} catch (IOException e) {}
		
		return outputStream.toByteArray();
	}

}
