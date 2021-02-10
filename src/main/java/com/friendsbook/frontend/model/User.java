package com.friendsbook.frontend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {
	
	@Id
	private int id;

	private String name, password, email;
	private boolean isAccountLocked;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<String>();
	
	@ElementCollection(fetch = FetchType.LAZY)
	private List<String> following = new ArrayList<String>();
	
	@ElementCollection(fetch = FetchType.LAZY)
	private List<String> followers = new ArrayList<String>();
	
	private Date joined, lastPasswordUpdated;
}
