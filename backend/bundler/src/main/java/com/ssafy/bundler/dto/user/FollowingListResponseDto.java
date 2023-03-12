package com.ssafy.bundler.dto.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowingListResponseDto {

	private Long userId; //fromUserId

	@Builder.Default
	private List<FollowProfileDto> followingList = new ArrayList<>();

	public void addFollowingList(FollowProfileDto followProfileDto) {
		followingList.add(followProfileDto);
	}

}
