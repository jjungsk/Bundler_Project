package com.ssafy.bundler.config.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@RequiredArgsConstructor
@SuperBuilder
public class JwtToken {

	private final String accessToken;
	private final String refreshToken;

}