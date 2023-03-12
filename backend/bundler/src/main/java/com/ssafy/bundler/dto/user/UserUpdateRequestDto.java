package com.ssafy.bundler.dto.user;

import com.ssafy.bundler.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequestDto {

	private Long userId;
	private String userNickname;
	private String userIntroduction;

	public User toEntity() {
		return User.builder()
			.userId(this.userId)
			.userNickname(userNickname)
			.userIntroduction(userIntroduction)
			.build();
	}

}
