package com.ssafy.bundler.dto.user;

import java.io.Serializable;

import com.ssafy.bundler.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupRequestDto implements Serializable {

	private String userEmail;
	private String userNickname;
	private String userPassword;
	private String userIntroduction;

	public User toEntity() {
		return User.builder()
			.userEmail(this.userEmail)
			.userNickname(this.userNickname)
			.userPassword(this.userPassword)
			.userIntroduction(this.userIntroduction)
			.build();
	}

}
