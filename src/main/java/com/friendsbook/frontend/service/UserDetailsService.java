package com.friendsbook.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.friendsbook.frontend.model.User;
import com.friendsbook.frontend.model.UserDetails;
import com.friendsbook.functional.UsersRepository;

@Component
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
	
	@Autowired
	private UsersRepository usrRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User usr = this.usrRepo.findByEmail(username);
		return (new UserDetails()).fromUser(usr);
	}

}
