package com.ssafy.bundler.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentSaveRequestDto {

	private Long userId;
	private Long targetFeedId;
	private String content;

}
