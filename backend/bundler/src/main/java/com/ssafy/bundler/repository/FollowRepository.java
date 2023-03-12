package com.ssafy.bundler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	int deleteByFollowFromIdAndFollowToId(Long fromUserId, Long toUserId);

	List<Follow> findByFollowFromId(Long fromUserId); //팔로잉 리스트

	boolean existsByFollowFromIdAndFollowToId(Long fromUserId, Long toUserId);

}

