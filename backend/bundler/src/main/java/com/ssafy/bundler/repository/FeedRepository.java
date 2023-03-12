package com.ssafy.bundler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.bundler.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {

	@Query("select f.feedId from Feed f where f.writer.userId =: userId")
	List<Long> findBundleIdsByUserId(@Param("userId") Long userId);

	@Query("select f from Feed f where f.writer.userId = :userId")
	List<Feed> findByUserId(Long userId);

	@Query("select f from Feed f where f.feedTitle like %:keyword%")
	List<Feed> findByFeedTitle(@Param("keyword") String keyword);
}
