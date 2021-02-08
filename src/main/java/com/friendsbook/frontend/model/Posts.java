package com.friendsbook.frontend.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Posts {
	
	private String owner, postText;
	private byte[] image;
	private Date created;
	
	public Posts(String owner){
		this.owner = owner;
	}
}

