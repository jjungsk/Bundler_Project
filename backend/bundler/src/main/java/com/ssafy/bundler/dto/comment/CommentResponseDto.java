package com.ssafy.bundler.dto.comment;

import java.time.LocalDateTime;

import com.ssafy.bundler.domain.Comment;

import lombok.Data;

@Data
public class CommentResponseDto {

	private Long commentId;
	private Long feedId;
	private Long commentWriterId;
	private String commentWriterNickname;
	private String commentWriterProfileImage;
	private String commentContent;
	private LocalDateTime createdAt;

	public CommentResponseDto(Comment comment) {
		this.commentId = comment.getCommentId();
		this.feedId = comment.getFeedId();
		this.commentWriterId = comment.getWriter().getUserId();
		this.commentWriterNickname = comment.getWriter().getUserNickname();
		this.commentWriterProfileImage = comment.getWriter().getUserProfileImage();
		this.commentContent = comment.getCommentContent();
		this.createdAt = comment.getCreatedAt();
	}

	public CommentResponseDto(Long commentId, Long feedId, Long commentWriterId, String commentWriterNickname,
		String commentWriterProfileImage, String commentContent, LocalDateTime createdAt) {
		this.commentId = commentId;
		this.feedId = feedId;
		this.commentWriterId = commentWriterId;
		this.commentWriterNickname = commentWriterNickname;
		this.commentWriterProfileImage = commentWriterProfileImage;
		this.commentContent = commentContent;
		this.createdAt = createdAt;
	}
}
