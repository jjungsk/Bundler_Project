package com.ssafy.bundler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.Bundle;
import com.ssafy.bundler.domain.Card;
import com.ssafy.bundler.domain.CardBundle;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.bundle.request.BundleSaveRequestDto;
import com.ssafy.bundler.dto.bundle.request.BundleScrapRequestDto;
import com.ssafy.bundler.dto.bundle.request.BundleUpdateRequestDto;
import com.ssafy.bundler.dto.feed.UserBundleListSummary;
import com.ssafy.bundler.repository.BundleRepository;
import com.ssafy.bundler.repository.CardBundleRepository;
import com.ssafy.bundler.repository.CardRepository;
import com.ssafy.bundler.repository.FeedRepository;
import com.ssafy.bundler.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 번들 생성, 수정, 삭제
 * 번들 스크랩
 * 1. 있던 번들 자체를 스크랩
 * 2. 카드 한개를 스크랩하면서 번들을 생성하고 넣는 스크랩
 *
 * @author 이혜지
 * @version 1.0
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BundleService {

	private final BundleRepository bundleRepository;
	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	private final CardBundleRepository cardBundleRepository;
	private final CardService cardService;
	private final FeedRepository feedRepository;

	//번들 생성
	//제일 기초인 빈 번들 생성
	@Transactional
	public Long saveBundle(BundleSaveRequestDto requestDto) {
		User writerUser = userRepository.findById(requestDto.getUserId()).orElseThrow(() ->
			new IllegalArgumentException("해당 유저가 존재하지 않습니다. userId= " + requestDto.getUserId()));

		String lineBreak = requestDto.getFeedContent().replace("\r\n", "<br>");
		requestDto.setFeedContent(lineBreak);

		// Integer size = validateFeedTitleAlreadyExistInUserBundle(requestDto.getFeedTitle());
		// if (size > 0) {
		// 	requestDto.setFeedTitle(requestDto.getFeedTitle() + "+" + size);
		// }

		return bundleRepository.save(requestDto.toEntity(writerUser)).getFeedId();
	}

	//카드와 함께 번들 생성
	@Transactional
	public void saveBundleWithCards(BundleSaveRequestDto requestDto) {

		Long savedBundleId = saveBundle(requestDto);
		List<Long> savedCardsIdList = cardService.saveCardListwithBundle(requestDto.getCardSaveRequestDtoList());

		for (Long savedCardId : savedCardsIdList) {
			saveCardBundle(savedBundleId, savedCardId);
		}
	}

	//번들 자체 스크랩
	@Transactional
	public void scrapBundleWithCards(BundleScrapRequestDto requestDto) {
		//일단 번들을 찾아서 피드의 정보를 가져와서 새번들을 만들어
		Bundle bundle = bundleRepository.findById(requestDto.getBundleId()).orElseThrow(() ->
			new IllegalArgumentException("해당 번들의 Id가 존재하지 않습니다. bundleId(feedId)= " + requestDto.getBundleId()));

		// if (bundle.getWriter().getUserId().equals(requestDto.getUserId())) {
		// 	throw new IllegalArgumentException("이미 사용자의 번들입니다.");
		// }

		//기본 정보를 복사한 빈 번들 생성
		BundleSaveRequestDto bundleSaveRequestDto = BundleSaveRequestDto.builder()
			.userId(requestDto.getUserId())
			.feedTitle(bundle.getFeedTitle())
			.feedContent(bundle.getFeedContent())
			.bundleThumbnail(bundle.getBundleThumbnail())
			.bundleThumbnailText(bundle.getBundleThumbnailText())
			.isBundleDefault(false)
			.isBundlePrivate(false)
			.build();

		//그리고 이 생성된 번들에 스크랩하려던 번들에 있던 카드를 다 때려박기
		Long saveBundleId = saveBundle(bundleSaveRequestDto);

		//CardBundle에서 스크랩할 대상의 bundleId로 조회하여 cardId 리스트를 가진다.
		List<CardBundle> allByBundleIdForScarpCard = cardBundleRepository.findAllByBundleId(requestDto.getBundleId());

		//카드번들 객체 생성으로 복사 완료
		for (CardBundle forScrapTargetCardBundle : allByBundleIdForScarpCard) {
			Long forScrapTargetCardId = forScrapTargetCardBundle.getCardId();
			saveCardBundle(saveBundleId, forScrapTargetCardId);
			Card card = getCard(forScrapTargetCardId);
			card.addCardScrapCnt();
		}

	}

	//번들 생성하면서 있던 카드를 스크랩
	@Transactional
	public void scrapCardWithSaveBundle(Long cardId, BundleSaveRequestDto requestDto) {
		Long savedBundleId = saveBundle(requestDto);
		saveCardBundle(savedBundleId, cardId);
		Card card = getCard(cardId);
		card.addCardScrapCnt();
	}

	//CardBundle 객체 생성
	@Transactional
	public void saveCardBundle(Long bundleId, Long cardId) {
		cardBundleRepository.save(CardBundle.builder()
			.bundleId(bundleId)
			.cardId(cardId)
			.build());
	}

	//번들 찐 정보 수정
	@Transactional
	public Long updateBundleInfo(Long feedId, BundleUpdateRequestDto requestDto) {
		Bundle findBundle = bundleRepository.findById(feedId).orElseThrow(() ->
			new IllegalArgumentException("해당 번들의 id가 존재하지 않습니다 bundleId(feedId)= " + feedId));

		bundleRepository.save(findBundle.toBuilder().feedId(feedId)
			.feedTitle(requestDto.getFeedTitle())
			.feedContent(requestDto.getFeedContent())
			.bundleThumbnail(requestDto.getBundleThumbnail())
			.bundleThumbnailText(requestDto.getBundleThumbnailText())
			.isBundlePrivate(requestDto.isBundlePrivate())
			.build()
		);

		return feedId;
	}

	//번들 삭제 ver1
	@Transactional
	public Long deleteBundleV1(Long feedId) {
		Bundle findBundle = bundleRepository.findById(feedId).orElseThrow(() ->
			new IllegalArgumentException("해당 카드를 찾을 수 없습니다. bundleId(feedId)= " + feedId));

		findBundle.deleteFeed();

		return feedId;
	}

	@Transactional
	public Long deleteBundleV2(Long feedId) {
		Bundle bundle = bundleRepository.findById(feedId).orElseThrow(() ->
			new IllegalArgumentException("해당 번들을 찾을 수 없습니다. bundleId= " + feedId));

		bundleRepository.delete(bundle);

		return feedId;
	}

	//번들에 있는 카드 스크랩 취소
	@Transactional
	public void scrapCancelCardInBundle(Long bundleId, Long cardId) {
		cardBundleRepository.deleteCardBundleByBundleIdWithCardId(bundleId, cardId);
	}

	//유저를 생성할때 기본 번들 생성 메서드 (유저 서비스에서 호출)
	public void saveDefaultBundle(Long userId) {

		bundleRepository.save(Bundle.builder()
			.writer(userRepository.findById(userId).get())
			.feedTitle("기본 번들")
			.isBundlePrivate(true)
			.isBundleDefault(true)
			.build());
	}

	//cardId로 Card객체 가져오기
	public Card getCard(Long cardId) {
		return cardRepository.findById(cardId).orElseThrow(() ->
			new IllegalArgumentException("해당 카드의 id가 존재하지 않습니다. cardId(feedId)= " + cardId));
	}

	public List<UserBundleListSummary> getUserBundleListSummary(Long userId, Long cardId) {

		cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("해당 카드는 존재하지 않습니다."));

		//사용자의 모든 피드 타이틀 들고오기
		List<UserBundleListSummary> result = bundleRepository.findAllFeedTitleByUserId(userId);

		List<Long> bundleIds = bundleRepository.findBundleIdsByUserId(userId);

		List<Boolean> existValue = new ArrayList<>();

		//해당 유저가 가지고잇는 번들들의 id를 돌면서 해당 번들에 카드가 있는지 확인
		for (Long bundleId : bundleIds) {
			existValue.add(validateCardAlreadyExistInBundle(bundleId, cardId));
		}

		int resultSize = result.size();
		for (int i = 0; i < resultSize; i++) {
			Boolean aBoolean = existValue.get(i);
			result.get(i).setAbleToInsertCardInBundle(aBoolean);
		}

		return result;
	}

	private boolean validateCardAlreadyExistInBundle(Long bundleId, Long cardId) {
		return cardBundleRepository.findCardBundleByBundleIdWithCardId(bundleId, cardId) == null;
	}

	private Integer validateFeedTitleAlreadyExistInUserBundle(String feedTitle) {
		List<Bundle> byFeedTitle = bundleRepository.findByFeedTitle(feedTitle);
		int size = byFeedTitle.size();

		if (size > 0) {
			return size;
		}
		return 0;
	}
}
