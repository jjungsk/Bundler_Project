package com.ssafy.bundler.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.bundler.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FollowResponseDTO implements Serializable {
	@Builder.Default
	private List<User> userList = new ArrayList<>();

	// public User toEntity() {
	// 	return User.builder()
	// 		.userEmail(this.userEmail)
	// 		.userNickname(this.userNickname)
	// 		.userPassword(Password.encrypt(this.userPassword))
	// 		.userIntroduction(this.userIntroduction)
	// 		.build();
	// }

}
