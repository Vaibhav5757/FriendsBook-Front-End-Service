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
	
	public UserDetails fromUser(User obj) {
		UserDetails usrDetails = new UserDetails();
		usrDetails.setUsername(obj.getEmail());
		usrDetails.setPassword(obj.getPassword());
		usrDetails.setLocked(obj.isAccountLocked());
		usrDetails.setLastPasswordUpdate(obj.getLastPasswordUpdated());
		// assign authorities
		usrDetails.setAuthorities(obj.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
		return usrDetails;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6578406613654039285L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	// expire password after 90 days
	@Override
	public boolean isCredentialsNonExpired() {
		return this.lastPasswordUpdate.getTime() < (new Date().getTime() - (90 * 24 * 60 * 60 * 1000));
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}