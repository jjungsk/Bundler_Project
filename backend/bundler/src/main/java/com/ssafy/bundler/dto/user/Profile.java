package com.ssafy.bundler.dto.user;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class Profile {

	private Long userId;
	private String userNickname;
	private String userProfileImageUrl;
	private String userIntroduction;

}
