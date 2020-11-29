package com.friendsbook.frontend.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{
	
	
	private String username, password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetails(User obj) {
		UserDetails usrDetails = new UserDetails();
		usrDetails.setUsername(obj.getEmail());
		usrDetails.setPassword(obj.getPassword());
		// assign authorities
		usrDetails.setAuthorities(obj.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6578406613654039285L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
