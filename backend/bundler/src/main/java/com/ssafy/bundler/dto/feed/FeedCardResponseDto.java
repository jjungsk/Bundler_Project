package com.ssafy.bundler.dto.feed;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.bundler.domain.CardType;
import com.ssafy.bundler.dto.comment.CommentResponseDto;

import lombok.Data;

@Data
public class FeedCardResponseDto {

	private Long feedId;
	// private String FeedType;

	private LocalDateTime updatedAt;

	private Long writerId;
	private String writerNickname;

	private Long categoryFirstId;

	private String categoryFristName;
	private Long categorySecondId;
	private String categorySecondName;

	private String feedTitle;
	private String feedContent;

	private CardType cardType;
	private String cardDescription;
	private String cardCommentary;

	private int cardLikeCnt;
	private int cardScrapCnt;
	private int cardCommentCnt;

	List<CommentResponseDto> feedCommentList;

	public FeedCardResponseDto(Long feedId, LocalDateTime updatedAt, Long writerId, String writerNickname,
		Long categoryFirstId, String feedTitle, String feedContent, CardType cardType, String cardDescription,
		String cardCommentary, int cardLikeCnt, int cardScrapCnt, int cardCommentCnt) {
		this.feedId = feedId;
		this.updatedAt = updatedAt;
		this.writerId = writerId;
		this.writerNickname = writerNickname;
		this.categoryFirstId = categoryFirstId;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.cardType = cardType;
		this.cardDescription = cardDescription;
		this.cardCommentary = cardCommentary;
		this.cardLikeCnt = cardLikeCnt;
		this.cardScrapCnt = cardScrapCnt;
		this.cardCommentCnt = cardCommentCnt;
	}

	public void setCategoryFirstSecond(Long categoryFirstId, String categoryFristName,
		Long categorySecondId, String categorySecondName) {

		this.categoryFristName = categoryFristName;
		this.categorySecondId = categorySecondId;
		this.categorySecondName = categorySecondName;
	}

	// public FeedCardResponseDto(Long feedId, LocalDateTime updatedAt, Long writerId, String writerNickname,
	// 	Long categoryFirstId, String categoryFristName, String feedTitle, String feedContent,
	// 	CardType cardType, String cardDescription, String cardCommentary, int cardLikeCnt, int cardScrapCnt,
	// 	int cardCommentCnt) {
	// 	this.feedId = feedId;
	// 	this.updatedAt = updatedAt;
	// 	this.writerId = writerId;
	// 	this.writerNickname = writerNickname;
	// 	this.categoryFirstId = categoryFirstId;
	// 	this.categoryFristName = categoryFristName;
	// 	this.feedTitle = feedTitle;
	// 	this.feedContent = feedContent;
	// 	this.cardType = cardType;
	// 	this.cardDescription = cardDescription;
	// 	this.cardCommentary = cardCommentary;
	// 	this.cardLikeCnt = cardLikeCnt;
	// 	this.cardScrapCnt = cardScrapCnt;
	// 	this.cardCommentCnt = cardCommentCnt;
	// }
}
