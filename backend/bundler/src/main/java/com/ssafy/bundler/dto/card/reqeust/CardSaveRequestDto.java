package com.ssafy.bundler.dto.card.reqeust;

import com.ssafy.bundler.domain.Card;
import com.ssafy.bundler.domain.CardType;
import com.ssafy.bundler.domain.Category;
import com.ssafy.bundler.domain.FeedType;
import com.ssafy.bundler.domain.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *packageName    : com.ssafy.bundler.dto.card.request
 * fileName       : CardSaveRequestDto
 * author         : modsiw
 * date           : 2023/02/04
 * description    : 카드 개별 조회 (댓글 리스트도 보임)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/05		 modsiw		  카테고리 대분류 중분류 필수 리팩토링
 */
@Data
public class CardSaveRequestDto {
	//문제, 일반, 링크를 한번에 받는다

	@NotEmpty
	private Long userId;

	private User writer;

	@NotEmpty
	private String feedType;

	@NotEmpty
	private String feedTitle;
	private String feedContent;

	@NotEmpty
	private Long categoryId;

	@NotEmpty
	private String cardType;

	private String cardDescription;
	private String cardCommentary;

	private Long feedId;

	private Category category;

	public Card toEntity(User writer, Category category) {
		return Card.builder()
			.feedType(FeedType.valueOf(this.feedType))
			.writer(writer)
			.feedTitle(this.feedTitle)
			.feedContent(this.feedContent)
			.cardType(CardType.valueOf(this.cardType))
			.cardDescription(this.cardDescription)
			.cardCommentary(this.cardCommentary)
			.category(category)
			.build();
	}

}
