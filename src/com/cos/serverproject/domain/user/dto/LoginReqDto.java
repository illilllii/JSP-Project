package com.cos.serverproject.domain.user.dto;

import lombok.Data;

@Data
public class LoginReqDto {
	private String username;
	private String password;
}
