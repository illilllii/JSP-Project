package com.cos.serverproject.domain.user.dto;

import lombok.Data;

@Data
public class joinReqDto {
	private String username;
	private String password;
	private String email;
}
