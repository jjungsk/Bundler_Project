package com.ssafy.bundler.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.dto.bundle.response.BundleResponseDto;
import com.ssafy.bundler.dto.card.response.CardSummaryResponseDto;
import com.ssafy.bundler.service.FeedService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *packageName    : com.ssafy.bundler.controller
 * fileName       : UserPageController
 * author         : modsiw
 * date           : 2023/02/06
 * description    : 사용자의 홈 컨트롤러 사용자 유저 홈을 조회하는데 로그인한 사용자인지 아닌지 분기함
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/06        modsiw       최초 생성
 * 2023/02/14		 modsiw		  번들리스트 (카드포함 x) 추가
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserFeedController {

	private final FeedService feedService;

	//해당 사용자의 홈으로 갔을 때 보여주는 번들 리스트
	//번들에 속하는 카드 포함
	@GetMapping("/v5/users/{user_id}/feeds/bundles")
	public List<BundleResponseDto> getBundlesFindByUserId(@PathVariable("user_id") Long userId) {
		//		UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();

		//		log.info("principal.getUserId : " + principal.getUserId());

		//		log.info(principal.getUsername());
		//		log.info(String.valueOf(principal.getUserId()));

		log.info("getBundlesFindByUserId() - " + SecurityContextHolder.getContext().getAuthentication().toString());

		org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		Long currentUserId = Long.parseLong(userPrincipal.getUsername());

		if (currentUserId.equals(userId)) {
			return feedService.getBundlesFindByUserIdContainIsBundlePrivate(userId);
		} else {
			return feedService.getBundlesFindByUserIdExceptIsBundlePrivate(userId);
		}
	}

	//번들만 카드 없이
	@GetMapping("/v4/users/{user_id}/feeds/bundles")
	public List<BundleResponseDto> getBundleFindByUserIdSummary(@PathVariable("user_id") Long userId) {

		org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		log.info("userPrincipal : " + userPrincipal);
		log.info("userPrincipal.toString() : " + userPrincipal.toString());

		Long currentUserId = Long.parseLong(userPrincipal.getUsername());

		if (currentUserId.equals(userId)) {
			return feedService.getBundlesFindByUserIdContainIsBundlePrivateSummary(
				userId);
		} else {
			return feedService.getBundlesFindByUserIdExceptIsBundlePrivateSummary(userId);
		}
	}

	//카드 리스트 반환
	@GetMapping("/v5/users/{user_id}/feeds/cards")
	public List<CardSummaryResponseDto> getCardsFindByUserId(@PathVariable("user_id") Long userId) {
		return feedService.getAllCardsFindByUserId(userId);
	}

	@GetMapping("/v5/users/main")
	public Result getFeedUserMain() throws Exception {
		org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		log.info("userPrincipal : " + userPrincipal);
		log.info("userPrincipal.toString() : " + userPrincipal.toString());

		Long userId = Long.parseLong(userPrincipal.getUsername());

		return new Result(feedService.findCardsAndBundlesByUserIdOnlyFollowing(userId));
	}

	@Data
	@AllArgsConstructor

	static class Result<T> {
		private T data;
	}
}
