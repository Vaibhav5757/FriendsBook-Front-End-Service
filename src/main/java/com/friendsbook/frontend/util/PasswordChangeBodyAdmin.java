package com.friendsbook.frontend.util;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeBodyAdmin {

	@NotBlank
	private String email, password;
}
