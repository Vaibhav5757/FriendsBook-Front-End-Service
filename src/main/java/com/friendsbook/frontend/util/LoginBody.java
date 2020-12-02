package com.friendsbook.frontend.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginBody {
	
	private String email, password;
}
