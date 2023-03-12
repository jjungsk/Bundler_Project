package com.ssafy.bundler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.dto.stat.StatCategoryDto;
import com.ssafy.bundler.dto.stat.StatResponseDto;
import com.ssafy.bundler.service.StatServiceImpl;

@RestController
public class StatController {
	@Autowired
	StatServiceImpl statServiceImpl;
	@GetMapping("/api/v1/users/{userId}/stats")
	public ResponseEntity<?> getStat(@PathVariable Long userId){
		StatCategoryDto[] statsTest= statServiceImpl.getCategoryStat(userId);
		String registerDate = statServiceImpl.getRegisterDate(userId);
		int totalFeedLikeCnt = statServiceImpl.getTotalFeedLike(userId);
		int totalCardScrapCnt = statServiceImpl.getTotalCardScrappedCount(userId);
		String[] maxCategories = statServiceImpl.getMaxCategories(userId);
		int mutualFollows = statServiceImpl.getMutualFollowCount(userId);

		double feedLikeRankingFollowing = statServiceImpl.getRankingFeedLikeFollowing(userId);
		double feedLikeRankingTotal = statServiceImpl.getRankingFeedLikeWhole(userId);
		double cardScrapRankingFollowing = statServiceImpl.getRankingCardScrapCntFollowing(userId);
		double cardScrapRankingTotal = statServiceImpl.getRankingCardScrapCntWhole(userId);
		double makeRankingTotal = statServiceImpl.getRankingCardCntWhole(userId);

		StatResponseDto responseDto = StatResponseDto.builder()
			.statCategory(statsTest)
			.registerDate(registerDate)
			.totalFeedLikeCnt(totalFeedLikeCnt)
			.totalCardScrapCnt(totalCardScrapCnt)
			.mostMakeCategory(maxCategories[0])
			.mostMakeSubCategory(maxCategories[1])
			.mutualFollows(mutualFollows)
			.feedLikeRankingFollowing(feedLikeRankingFollowing)
			.feedLikeRankingTotal(feedLikeRankingTotal)
			.cardScrapRankingFollowing(cardScrapRankingFollowing)
			.cardScrapRankingTotal(cardScrapRankingTotal)
			.cardMakeRankingTotal(makeRankingTotal)
			.build();

		return ResponseEntity.ok(responseDto);
	}
}
