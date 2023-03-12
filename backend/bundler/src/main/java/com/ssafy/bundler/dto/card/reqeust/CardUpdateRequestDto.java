package com.ssafy.bundler.dto.card.reqeust;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
public class CardUpdateRequestDto {
	@NotEmpty
	private String feedTitle;
	private String feedContent;

	@NotEmpty
	private Long categoryId;

	private String cardDescription;
	private String cardCommentary;

	@Builder
	public CardUpdateRequestDto(String feedTitle, String feedContent, Long categoryId,
		String cardDescription, String cardCommentary) {
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.categoryId = categoryId;
		this.cardDescription = cardDescription;
		this.cardCommentary = cardCommentary;
	}
}
