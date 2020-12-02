package com.friendsbook.frontend.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6136619798115763049L;
	
	private String msg;
	
	public UnauthorizedException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
