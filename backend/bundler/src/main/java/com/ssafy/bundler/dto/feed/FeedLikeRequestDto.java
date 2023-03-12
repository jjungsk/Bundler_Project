package com.ssafy.bundler.dto.feed;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@Data
@JsonAutoDetect
public class FeedLikeRequestDto {
	private long userId;
}
