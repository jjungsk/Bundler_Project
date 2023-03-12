package com.ssafy.bundler.dto.feed;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FeedLikeResponseDto {
	private final boolean like;
}
