package com.ssafy.bundler.dto.bundle.response;

import java.time.LocalDateTime;

import lombok.Data;

/**
 *packageName    : com.ssafy.bundler.dto.bundle.response
 * fileName       : FeedResponseDto
 * author         : modsiw
 * date           : 2023/02/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/04        modsiw       최초 생성
 */

@Data
public class FeedResponseDtoTest {
	private Long bundleId;
	// private String feedType;

	private LocalDateTime createdAt;

	private Long bundleWriterId;
	private String bundleWriterProfileImage;
	private String bundleWriterNickname;

	private String feedTitle;
	private String feedContent;

	private String bundleThumbnail;
	private String bundleThumbnailText;

	private Long cardId;
	private String feedType;

	private String cardType;

	private boolean isDeleted;

	private Long userId;
	private String userProfileImage;
	private String userNickname;

	private Long firstCategoryId;
	private String firstCategoryName;
	private Long secondCategoryId;
	private String secondCategoryName;

	private String cardDescription;
	private String cardCommentary;

	private Long linkId;
	private String linkUrl;
	private String linkImage;
	private String linkTitle;
	private String linkDescription;

	private boolean isBundlePublic;

	private int cardScrapCnt;
	private int feedLikeCnt;
	private int feedCommentCnt;
}