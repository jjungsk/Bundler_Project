package com.ssafy.bundler.dto.stat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatResponseDto {
	private StatCategoryDto[] statCategory;
	private String registerDate;
	private int mutualFollows;
	private int continuousCardMakeCnt;
	private int totalFeedLikeCnt;
	private int totalCardScrapCnt;
	private String mostMakeCategory;
	private String mostMakeSubCategory;

	private double feedLikeRankingFollowing;
	private double cardScrapRankingFollowing;
	private double feedLikeRankingTotal;
	private double cardScrapRankingTotal;
	private double cardMakeRankingTotal;

}
