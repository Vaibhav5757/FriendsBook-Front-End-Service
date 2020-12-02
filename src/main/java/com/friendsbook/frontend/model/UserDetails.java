package com.friendsbook.frontend.model;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{
	
	
	private String username, password;
	private boolean locked;
	private Date lastPasswordUpdate;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetails(User obj) {
		UserDetails usrDetails = new UserDetails();
		usrDetails.setUsername(obj.getEmail());
		usrDetails.setPassword(obj.getPassword());
		usrDetails.setLocked(obj.isAccountLocked());
		usrDetails.setLastPasswordUpdate(obj.getLastPasswordUpdated());
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
		return !this.locked;
	}

	// expire password after 90 days
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.lastPasswordUpdate.getTime() < (new Date().getTime() - (90 * 24 * 60 * 60 * 1000));
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
