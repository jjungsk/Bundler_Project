package com.ssafy.bundler.dto.card.response;

import java.time.LocalDateTime;

import com.ssafy.bundler.domain.Card;
import com.ssafy.bundler.domain.FeedType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CardSummaryResponseDto {

	private Long cardId;
	private String feedType = FeedType.CARD.toString();

	private LocalDateTime createdAt;
	private String cardType;

	// private boolean isDeleted;

	private Long userId;
	private String userProfileImage;
	private String userNickname;

	private Long firstCategoryId;
	private String firstCategoryName;
	private Long secondCategoryId;
	private String secondCategoryName;

	private String feedTitle;
	private String feedContent;
	private String cardDescription;
	private String cardCommentary;

	// private Long linkId;
	// private String linkUrl;
	// private String linkImage;
	// private String linkTitle;
	// private String linkDescription;

	private int cardScrapCnt;
	private int feedLikeCnt;
	private int feedCommentCnt;

	public CardSummaryResponseDto(Card card) {
		this.cardId = card.getFeedId();
		this.cardType = card.getCardType().toString();
		this.createdAt = card.getCreatedAt();
		// this.isDeleted = card.isDeleted();
		this.userId = card.getWriter().getUserId();
		this.userProfileImage = card.getWriter().getUserProfileImage();
		this.userNickname = card.getWriter().getUserNickname();
		if (card.getCategory().getParent() == null) {
			this.firstCategoryId = card.getCategory().getCategoryId();
			this.firstCategoryName = card.getCategory().getCategoryName();
		} else if (card.getCategory().getParent() != null) {
			this.firstCategoryId = card.getCategory().getParent().getCategoryId();
			this.firstCategoryName = card.getCategory().getParent().getCategoryName();
			this.secondCategoryId = card.getCategory().getCategoryId();
			this.secondCategoryName = card.getCategory().getCategoryName();

		}
		this.feedTitle = card.getFeedTitle();
		this.feedContent = card.getFeedContent();
		this.cardDescription = card.getCardDescription();
		this.cardCommentary = card.getCardCommentary();
		this.cardScrapCnt = card.getCardScrapCnt();
		this.feedLikeCnt = card.getFeedLikeCnt();
		this.feedCommentCnt = card.getFeedCommentCnt();
	}
}
