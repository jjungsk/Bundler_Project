package com.ssafy.bundler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.bundler.domain.Bundle;
import com.ssafy.bundler.dto.feed.UserBundleListSummary;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

	@Query("select new com.ssafy.bundler.dto.feed.UserBundleListSummary"
		+ "(b.bundleId, b.feedTitle) from Bundle b where b.writer.userId = :userId")
	List<UserBundleListSummary> findAllFeedTitleByUserId(@Param("userId") Long userId);

	@Query("select b.bundleId from Bundle b where b.writer.userId = :userId")
	List<Long> findBundleIdsByUserId(@Param("userId") Long userId);

	@Query("select b from Bundle b where b.feedTitle = :feedTitle")
	List<Bundle> findByFeedTitle(@Param("feedTitle") String feedTitle);

	Bundle findByBundleThumbnailFileName(String fileName);
}
