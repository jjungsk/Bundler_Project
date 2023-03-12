package com.ssafy.bundler.service;

import org.springframework.stereotype.Service;

import com.ssafy.bundler.dto.stat.StatCategoryDto;

@Service
public interface StatService {
	StatCategoryDto[] getCategoryStat(Long userId);
	String getRegisterDate(Long userId);
	Integer getTotalFeedLike(Long userId);
	Integer getTotalCardScrappedCount(Long userId);
	String[] getMaxCategories(Long userId);
	int getMutualFollowCount(Long userId);
	double getRankingFeedLikeWhole(Long userId);
	double getRankingFeedLikeFollowing(Long userId);
	double getRankingCardScrapCntWhole(Long userId);

	double getRankingCardScrapCntFollowing(Long userId);
	double getRankingCardCntWhole(Long userId);
}
