package com.ssafy.bundler.dto;

import com.ssafy.bundler.domain.ProviderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	@NotNull
	private String providerUserId;

	@NotNull
	private ProviderType providerType;

	@NotBlank
	private String userNickname;

	@NotNull
	private String userEmail;
}
