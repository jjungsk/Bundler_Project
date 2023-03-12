package com.ssafy.bundler.dto.bundle.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.bundler.domain.FeedType;
import com.ssafy.bundler.dto.comment.CommentResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 번들 조회시 사용되는 dto
 *
 * @author 이혜지
 * @version 1.0
 */

@Data
@RequiredArgsConstructor
public class BundleResponseDto {

	private Long bundleId;
	private String feedType = FeedType.BUNDLE.toString();

	private LocalDateTime createdAt;

	private Long bundleWriterId;
	private String bundleWriterProfileImage;
	private String bundleWriterNickname;

	private String feedTitle;
	private String feedContent;

	private String bundleThumbnail;
	private String bundleThumbnailText;

	//댓글 수, 좋아요 수
	private int feedLikeCnt;
	private int feedCommentCnt;

	//번들이 디폴트 번들인지
	private boolean isBundleDefault;
	//번들의 공개여부
	private boolean isBundlePrivate;

	//번들의 댓글들
	List<CommentResponseDto> bundleCommentResponseList;

	//번들에 담겨있는 카드의 리스트
	List<CardBundleQueryDto> cardBundleQueryDtoList;

	public BundleResponseDto(Long bundleId, LocalDateTime createdAt, Long bundleWriterId,
		String bundleWriterProfileImage, String bundleWriterNickname, String feedTitle, String feedContent,
		String bundleThumbnail, String bundleThumbnailText, int feedLikeCnt, int feedCommentCnt,
		boolean isBundleDefault, boolean isBundlePrivate) {
		this.bundleId = bundleId;
		this.createdAt = createdAt;
		this.bundleWriterId = bundleWriterId;
		this.bundleWriterProfileImage = bundleWriterProfileImage;
		this.bundleWriterNickname = bundleWriterNickname;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.bundleThumbnail = bundleThumbnail;
		this.bundleThumbnailText = bundleThumbnailText;
		this.feedLikeCnt = feedLikeCnt;
		this.feedCommentCnt = feedCommentCnt;
		this.isBundleDefault = isBundleDefault;
		this.isBundlePrivate = isBundlePrivate;
	}

	public BundleResponseDto(Long bundleId, LocalDateTime createdAt, Long bundleWriterId,
		String bundleWriterProfileImage, String bundleWriterNickname, String feedTitle, String feedContent,
		String bundleThumbnail, String bundleThumbnailText, int feedLikeCnt, int feedCommentCnt) {
		this.bundleId = bundleId;
		this.createdAt = createdAt;
		this.bundleWriterId = bundleWriterId;
		this.bundleWriterProfileImage = bundleWriterProfileImage;
		this.bundleWriterNickname = bundleWriterNickname;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.bundleThumbnail = bundleThumbnail;
		this.bundleThumbnailText = bundleThumbnailText;
		this.feedLikeCnt = feedLikeCnt;
		this.feedCommentCnt = feedCommentCnt;
	}
}
