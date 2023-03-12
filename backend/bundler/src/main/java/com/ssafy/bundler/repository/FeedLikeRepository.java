package com.ssafy.bundler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.Feed;
import com.ssafy.bundler.domain.FeedLike;

public interface FeedLikeRepository extends JpaRepository<FeedLike,Long> {
	FeedLike findByFeedAndUserId(Feed feed,Long userId);
}
