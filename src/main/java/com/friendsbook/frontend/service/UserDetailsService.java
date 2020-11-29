package com.friendsbook.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.friendsbook.repository.UsersRepository;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
	
	@Autowired
	private UsersRepository usrRepo;

	@Override
	public com.friendsbook.frontend.model.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new com.friendsbook.frontend.model.UserDetails(this.usrRepo.findByEmail(username));
	}

}
