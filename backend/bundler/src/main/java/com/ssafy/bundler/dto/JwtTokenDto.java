package com.ssafy.bundler.dto;

import com.ssafy.bundler.config.jwt.JwtToken;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class JwtTokenDto extends JwtToken {

	private final Long userId;
	private final String userEmail;
	private final String nickname;

}
