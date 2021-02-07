package com.friendsbook.frontend.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.friendsbook.frontend.model.Posts;
import com.friendsbook.frontend.security.JwtProvider;
import com.friendsbook.frontend.service.PostService;
import com.friendsbook.frontend.util.ApiResponse;
import com.friendsbook.frontend.util.CustomException;

import lombok.extern.java.Log;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/post")
@Log
public class PostController {
	
	@Autowired
	private PostService postSvc;
	
	@PostMapping(value = "/post")
	public ResponseEntity<ApiResponse> post(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file, @RequestParam("text") String text) throws IOException, CustomException{
		return this.postSvc.post(token, file, text);
	}
	
	@GetMapping("/getPosts")
	public Posts[] getAllPosts(@RequestParam("author") String author){
		return this.postSvc.getPosts(author);
	}
}
