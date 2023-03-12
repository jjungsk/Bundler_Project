package com.ssafy.bundler.dto.user;

import com.ssafy.bundler.domain.ProviderType;
import com.ssafy.bundler.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserMypageResponseDto {
	Long userId;
	String userEmail;
	String userNickName;
	String userIntroduction;
	String userProfileImage;
	String userGithubUrl;
	Integer userFollowingCount;
	Integer userFollowerCount;
	ProviderType providerType;

	@Setter
	boolean isFollowing;

	UserCalendarResponseDto userCalendar;

	public void userInit(User user) {
		this.userId = user.getUserId();
		this.userEmail = user.getUserEmail();
		this.userNickName = user.getUserNickname();
		this.userIntroduction = user.getUserIntroduction();
		this.userProfileImage = user.getUserProfileImage();
		this.userGithubUrl = user.getGithubUrl();
		this.userFollowingCount = user.getFollowingCnt();
		this.userFollowerCount = user.getFollowerCnt();
		this.providerType = user.getProviderType();
	}

}
