package com.ssafy.bundler.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.dto.bundle.response.BundleResponseDto;
import com.ssafy.bundler.dto.card.response.CardResponseDto;
import com.ssafy.bundler.dto.card.response.CardSummaryResponseDto;
import com.ssafy.bundler.dto.feed.FeedLikeRequestDto;
import com.ssafy.bundler.dto.feed.FeedLikeResponseDto;
import com.ssafy.bundler.repository.FeedRepository;
import com.ssafy.bundler.repository.query.BundleQueryRepository;
import com.ssafy.bundler.repository.query.CardQueryRepository;
import com.ssafy.bundler.service.FeedService;

import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.ssafy.bundler.controller
 * fileName       : FeedController
 * author         : modsiw
 * date           : 2023/02/04
 * description    : 피드 조회 컨트롤러 : 카드개별, 카드전체, 번들개별, 번들전체, 피드전체
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/04        modsiw       최초 생성
 * 2023/02/05		 modsiw		  번들 개별 조회 추가
 * 2023/02/-6		 modsiw		  사용자 아이디로 카드리스트 번들 리스트 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedController {
	private final FeedRepository feedRepository;
	private final FeedService feedService;
	private final BundleQueryRepository budnleQueryRepository;
	private final CardQueryRepository cardQueryRepository;

	//카드 리스트 V1 (댓글 없음)
	@GetMapping("/v1/feeds/cards")
	public List<CardSummaryResponseDto> getCardsV1() {
		return feedService.findCardSummanryList();
	}

	//카드 리스트 V2 (댓글 있음)
	@GetMapping("/v5/feeds/cards")
	public List<CardResponseDto> getCardsWithCommentList() {
		return cardQueryRepository.findAllCardByDto_optimization();
	}

	//카드 개별
	@GetMapping("/v1/feeds/cards/{feed_id}")
	public CardResponseDto getCard(@PathVariable("feed_id") Long feedId) {
		return feedService.findCard(feedId);
	}

	//번들 리스트 V1 (댓글 있음)
	// @GetMapping("/v1/feeds/bundles")
	// public List<BundleSummaryResponseDto> getBundlesV1() {
	// 	return feedService.find
	// }

	//번들 리스트 V2 (댓글 없음)
	@GetMapping("/v5/feeds/bundles")
	public List<BundleResponseDto> getBundlesWithCommentList() {
		return budnleQueryRepository.findAllBundleByDto_optimization();
	}

	//번들 개별
	@GetMapping("/v5/feeds/bundles/{feed_id}")
	public BundleResponseDto getBundle(@PathVariable("feed_id") Long feedId) {
		return budnleQueryRepository.findBundleByDto_optimization(feedId);
	}

	//전체 조회
	@GetMapping("/v1/feeds")
	public ResponseEntity<List<Object>> getFeedsV1() {
		List<Object> test = feedService.getAllFeed();
		return new ResponseEntity<>(test, HttpStatus.OK);
	}

	//전체 조회 test
	@GetMapping("/test/feeds")
	public ResponseEntity<List<Object>> getFeeds(@RequestParam("keyword") String keyword) {
		List<Object> allByKeyword = feedService.findAllByKeyword(keyword);
		return new ResponseEntity<List<Object>>(allByKeyword, HttpStatus.OK);
	}

	@PostMapping("/v1/feeds/like/{feed_id}")
	public ResponseEntity<?> likeFeed(@PathVariable("feed_id") Long feedId,
		@RequestBody FeedLikeRequestDto feedLikeRequestDto) {
		FeedLikeResponseDto responseDto = new FeedLikeResponseDto(
			feedService.likeFeed(feedId, Long.parseLong(String.valueOf(feedLikeRequestDto.getUserId())))
		);
		return new ResponseEntity<FeedLikeResponseDto>(responseDto, HttpStatus.OK);
	}

	@GetMapping("/v1/feeds/like/{feed_id}")
	public ResponseEntity<?> islikeFeed(@PathVariable("feed_id") Long feedId,
		@RequestParam("user_id") Long userId) {
		FeedLikeResponseDto responseDto = new FeedLikeResponseDto(
			feedService.islikeFeed(feedId, userId)
		);
		return new ResponseEntity<FeedLikeResponseDto>(responseDto, HttpStatus.OK);
	}

}
