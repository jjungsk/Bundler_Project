package com.ssafy.bundler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.FeedCategory;

public interface FeedCategoryRepository extends JpaRepository<FeedCategory, Long> {

	FeedCategory findByFeedId(Long feedId);

}
