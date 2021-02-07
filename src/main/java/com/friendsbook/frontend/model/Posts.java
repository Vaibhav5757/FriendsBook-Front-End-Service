package com.friendsbook.frontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Posts {
	
	private String owner, postText;
	private byte[] image;
	
	public Posts(String owner){
		this.owner = owner;
	}
}

