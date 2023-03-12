package com.ssafy.bundler.dto.bundle.request;

import java.util.List;

import com.ssafy.bundler.domain.Bundle;
import com.ssafy.bundler.domain.FeedType;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.card.reqeust.CardSaveRequestDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BundleSaveRequestDto {

	@NotEmpty
	private Long userId;
	// private CardListSaveRequestDto cardListSaveRequestDto;

	private String feedType = "BUNDLE";

	private String bundleThumbnail;
	private String bundleThumbnailText;

	@NotEmpty
	private String feedTitle;
	@NotEmpty
	private String feedContent;

	private boolean isBundlePrivate = false;
	private boolean isBundleDefault = false;

	private List<CardSaveRequestDto> cardSaveRequestDtoList;

	@Builder
	public BundleSaveRequestDto(Long userId, String bundleThumbnail, String bundleThumbnailText,
		String feedTitle, String feedContent, boolean isBundlePrivate, boolean isBundleDefault) {
		this.userId = userId;
		this.bundleThumbnail = bundleThumbnail;
		this.bundleThumbnailText = bundleThumbnailText;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.isBundlePrivate = isBundlePrivate;
		this.isBundleDefault = isBundleDefault;
	}

	public Bundle toEntity(User writer) {
		return Bundle.builder()
			.writer(writer)
			.feedType(FeedType.valueOf(this.feedType))
			.feedTitle(this.feedTitle)
			.feedContent(this.feedContent)
			.bundleThumbnail(this.bundleThumbnail)
			.bundleThumbnailText(this.bundleThumbnailText)
			.isBundlePrivate(this.isBundlePrivate)
			.isBundleDefault(this.isBundleDefault)
			.build();
	}
}
