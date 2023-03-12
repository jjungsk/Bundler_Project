package com.ssafy.bundler.dto.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class FollowerListResponseDto {

	private Long userId; //fromUserId

	@Builder.Default
	@Setter
	private List<FollowProfileDto> followerList = new ArrayList<>();

	public void addFollowerList(FollowProfileDto followProfileDto) {
		followerList.add(followProfileDto);
	}

}
