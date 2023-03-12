package com.ssafy.bundler.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.dto.bundle.request.BundleSaveRequestDto;
import com.ssafy.bundler.dto.bundle.request.BundleScrapRequestDto;
import com.ssafy.bundler.dto.feed.UserBundleListSummary;
import com.ssafy.bundler.service.BundleService;
import com.ssafy.bundler.service.CardService;

import lombok.RequiredArgsConstructor;

/**
 * 번들 스크랩, 카드 스크랩, 카드 스크랩시 번들 함께 생성 컨트롤러
 *
 * @author 이혜지
 * @version 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController {

	private final CardService cardService;
	private final BundleService bundleService;

	//번들 스크랩
	@PostMapping("/scrap/bundles")
	public ResponseEntity<?> bundleScrap(@RequestBody BundleScrapRequestDto requestDto) {
		bundleService.scrapBundleWithCards(requestDto);

		return ResponseEntity.ok("번들 스크랩 성공.");
	}

	//카드를 번들을 생성하면서 스크랩
	@PostMapping("/scrap/cards/bundles/{feed_id}")
	public ResponseEntity<?> cardScrapWithCreateBundle(@PathVariable("feed_id") Long feedId,
		@RequestBody BundleSaveRequestDto requestDto) {
		bundleService.scrapCardWithSaveBundle(feedId, requestDto);

		return ResponseEntity.ok("새로운 번들에 카드 스크랩 성공.");
	}

	//카드를 이미 존재하는 번들에 스크랩
	@PutMapping("/scrap/cards")
	public ResponseEntity<?> cardScrapWithExistBundle(@RequestBody BundleScrapRequestDto requestDto) {
		cardService.scrapCardWithExistBundle(requestDto);

		return ResponseEntity.ok("기존의 번들에 카드 스크랩 성공.");
	}

	//번들에 존재하는 카드 목록중 하나를 삭제할 때
	@DeleteMapping("/scrap/bundles/{bundle_id}/cards/{card_id}")
	public ResponseEntity<?> scrapCancelCardInBundle(@PathVariable("bundle_id") Long bundleId,
		@PathVariable("card_id") Long cardId) {
		bundleService.scrapCancelCardInBundle(bundleId, cardId);

		return ResponseEntity.ok("번들에서 카드 스크랩 취소");
	}

	// //유저가 스크랩할때 어디 번들에 넣을지 보여주는 번들 제목 리스트 (번들 아이디 + 번들 제목)
	@GetMapping("/users/{user_id}/bundles/summary")
	public List<UserBundleListSummary> getUserBundleSummaryList(@PathVariable(name = "user_id") Long userId,
		@RequestParam(name = "card_id") Long cardId) {
		return bundleService.getUserBundleListSummary(userId, cardId);
	}

}
