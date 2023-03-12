package com.ssafy.bundler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.domain.CardType;
import com.ssafy.bundler.dto.card.reqeust.CardListSaveRequestDto;
import com.ssafy.bundler.dto.card.reqeust.CardSaveRequestDto;
import com.ssafy.bundler.dto.card.reqeust.CardUpdateRequestDto;
import com.ssafy.bundler.service.CardService;

import lombok.RequiredArgsConstructor;

/**
 * 카드 생성 수정 삭제 컨트롤러
 *
 * @author 이혜지
 * @version 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {

	private final CardService cardService;

	//카드 리스트로 받아왔을 때 생성
	@PostMapping("/v1/cards/list")
	public ResponseEntity<?> saveCardList(@RequestBody CardListSaveRequestDto requestDto) {
		cardService.saveCardList(requestDto);

		return ResponseEntity.ok("리스트 카드 개별 생성 성공.");
	}

	//개별 카드 생성
	@PostMapping("/v1/cards")
	public ResponseEntity<?> saveCard(@RequestBody CardSaveRequestDto requestDto) {
		//문제, 일반 -> 하나로 분기, link -> 분기 다른거
		String cardType = requestDto.getCardType();

		if (CardType.CARD_LINK.toString().equals(cardType)) {
			cardService.saveCard(requestDto);
		} else {
			cardService.saveCard(requestDto);
		}
		return ResponseEntity.ok("카드 개별 생성 성공.");
	}

	//카드 정보 수정
	@PutMapping("/v1/cards/{feed_id}")
	public ResponseEntity<?> updateCard(@PathVariable("feed_id") Long feedId,
		@RequestBody CardUpdateRequestDto requestDto) {
		cardService.updateCard(feedId, requestDto);
		return ResponseEntity.ok("카드 업데이트 완료");
	}

	//카드 삭제 V1
	@DeleteMapping("/v1/cards/{feed_id}")
	public ResponseEntity<?> deleteCardV1(@PathVariable("feed_id") Long feedId) {
		cardService.deleteCardV1(feedId);
		return ResponseEntity.ok("카드 삭제 완료.");
	}

	//카드 찐 삭제 V2
	@DeleteMapping("/v2/cards/{feed_id}")
	public ResponseEntity<?> deleteCardV2(@PathVariable("feed_id") Long feedId) {
		cardService.deleteCardV2(feedId);

		return ResponseEntity.ok("카드 삭제 완료." + feedId);
	}

}
