package com.ssafy.bundler.dto.feed;

import com.ssafy.bundler.domain.Card;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FeedResponseDto {

	private Long feedId;
	private String feedType;

	// private LocalDateTime createdAt;
	private String cardType;

	private boolean isDeleted;

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

	private Long linkId;
	private String linkUrl;
	private String linkImage;
	private String linkTitle;
	private String linkDescription;

	private String bundleThumbnail;
	private String bundleThumbnailText;
	private boolean isBundlePublic;

	private int cardScrapCnt;
	private int feedLikeCnt;
	private int feedCommentCnt;

	public FeedResponseDto(Card card) {
		this.feedId = card.getFeedId();
		this.cardType = card.getCardType().toString();
		this.isDeleted = card.isDeleted();
		this.userId = card.getWriter().getUserId();
		this.userProfileImage = card.getWriter().getUserProfileImage();
		this.userNickname = card.getWriter().getUserNickname();
		this.firstCategoryId = card.getCategory().getParent().getCategoryId();
		this.firstCategoryName = card.getCategory().getParent().getCategoryName();
		this.secondCategoryId = card.getCategory().getCategoryId();
		this.secondCategoryName = card.getCategory().getCategoryName();
		this.feedTitle = card.getFeedTitle();
		this.feedContent = card.getFeedContent();
		this.cardDescription = card.getCardDescription();
		this.cardCommentary = card.getCardCommentary();
		// this.linkId = linkId;
		// this.linkUrl = linkUrl;
		// this.linkImage = linkImage;
		// this.linkTitle = linkTitle;
		// this.linkDescription = linkDescription;
		// this.bundleThumbnail = bundleThumbnail;
		// this.bundleThumbnailText = bundleThumbnailText;
		// this.isBundlePublic = isBundlePublic;
		this.cardScrapCnt = card.getCardScrapCnt();
		this.feedLikeCnt = card.getFeedLikeCnt();
		this.feedCommentCnt = card.getFeedCommentCnt();
	}
}
