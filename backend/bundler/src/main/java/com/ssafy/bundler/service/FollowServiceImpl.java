package com.ssafy.bundler.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.Follow;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.user.FollowProfileDto;
import com.ssafy.bundler.dto.user.FollowerListResponseDto;
import com.ssafy.bundler.dto.user.FollowingListResponseDto;
import com.ssafy.bundler.exception.business.UserNotFoundException;
import com.ssafy.bundler.repository.FollowRepository;
import com.ssafy.bundler.repository.UserRepository;
import com.ssafy.bundler.repository.query.FollowQueryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService {
	private final UserRepository userRepository;

	private final FollowRepository followRepository;
	private final FollowQueryRepository followQueryRepository;

	@Override
	public void followUser(Long fromUserId, Long toUserId) {
		User followFromUser = userRepository.findOneByUserId(fromUserId)
			.orElseThrow(() -> new UserNotFoundException("followFromUser 없음"));

		User followToUser = userRepository.findOneByUserId(toUserId)
			.orElseThrow(() -> new UserNotFoundException("followToUser 없음"));

		followRepository.save(
			Follow.builder()
				.followFromId(fromUserId)
				.followToId(toUserId)
				.followFrom(followFromUser.toBuilder()
					.followingCnt(followFromUser.getFollowingCnt() + 1)
					.build())
				.followTo(followToUser.toBuilder()
					.followerCnt(followToUser.getFollowerCnt() + 1)
					.build())
				.build()
		);
	}

	@Override
	public boolean unfollowUser(Long fromUserId, Long toUserId) {
		User followFromUser = userRepository.findOneByUserId(fromUserId)
			.orElseThrow(() -> new UserNotFoundException("unfollowFromUser 없음"));

		User followToUser = userRepository.findOneByUserId(toUserId)
			.orElseThrow(() -> new UserNotFoundException("unfollowToUser 없음"));

		userRepository.save(
			followFromUser.toBuilder()
				.followingCnt(followFromUser.getFollowingCnt() - 1)
				.build()
		);

		userRepository.save(
			followToUser.toBuilder()
				.followingCnt(followToUser.getFollowerCnt() - 1)
				.build()
		);

		return followRepository.deleteByFollowFromIdAndFollowToId(fromUserId, toUserId) > 0;
	}

	@Override
	public FollowingListResponseDto getUserFollowingList(Long userId) {
		List<Follow> follow = followRepository.findByFollowFromId(userId);

		if (follow != null && follow.size() > 0) {
			FollowingListResponseDto followingListResponseDto = FollowingListResponseDto.builder()
				.userId(follow.get(0).getFollowFromId())
				.build();

			follow.forEach(f -> {
				followingListResponseDto.addFollowingList(
					FollowProfileDto.builder()
						.followId(f.getFollowId())
						.userNickname(f.getFollowTo().getUserNickname())
						.userProfileImageUrl(f.getFollowTo().getUserProfileImage())
						.userIntroduction(f.getFollowTo().getUserIntroduction())
						.userId(f.getFollowToId())
						.isFollowBack(true)
						.build());
			});

			return followingListResponseDto;
		}

		return FollowingListResponseDto.builder()
			.userId(userId)
			.build();
	}

	@Override
	public FollowerListResponseDto getUserFollowerList(Long userId) {
		List<Follow> follow = followQueryRepository.findByFollowToId(userId);

		if (follow != null && follow.size() > 0) {
			FollowerListResponseDto followerListResponseDto = FollowerListResponseDto.builder()
				.userId(follow.get(0).getFollowToId())
				.build();

			follow.forEach(f -> {
				FollowProfileDto followProfileDto = FollowProfileDto.builder()
					.followId(f.getFollowId())
					.userNickname(f.getFollowFrom().getUserNickname())
					.userProfileImageUrl(f.getFollowFrom().getUserProfileImage())
					.userIntroduction(f.getFollowFrom().getUserIntroduction())
					.userId(f.getFollowFromId())
					.build();

				if (f.getFollowBackId() != null) { //맞팔이면
					followProfileDto.setFollowBack(true);
				}

				followerListResponseDto.addFollowerList(followProfileDto);
			});

			return followerListResponseDto;
		}

		return FollowerListResponseDto.builder()
			.userId(userId)
			.build();
	}

	@Override
	public boolean isFollowing(Long fromUserId, Long toUserId) {
		return followRepository.existsByFollowFromIdAndFollowToId(fromUserId, toUserId);
	}

}
