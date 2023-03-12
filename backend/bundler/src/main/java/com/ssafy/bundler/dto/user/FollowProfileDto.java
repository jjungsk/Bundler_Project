package com.ssafy.bundler.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FollowProfileDto extends Profile {

	private Long followId;

	@Setter
	private boolean isFollowBack; //맞팔 여부

}
