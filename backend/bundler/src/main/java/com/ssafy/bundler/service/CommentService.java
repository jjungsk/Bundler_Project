package com.ssafy.bundler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.Comment;
import com.ssafy.bundler.domain.Feed;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.comment.CommentSaveRequestDto;
import com.ssafy.bundler.dto.comment.CommentUpdateRequestDto;
import com.ssafy.bundler.repository.CommentRepository;
import com.ssafy.bundler.repository.FeedRepository;
import com.ssafy.bundler.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	FeedRepository feedRepository;
	private final UserRepository userRepository;

	public Comment saveComment(CommentSaveRequestDto commentDto) {

		User user = userRepository.findByUserId(commentDto.getUserId()).orElseThrow(
			() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + commentDto.getUserId()));

		Feed targetFeed = feedRepository.findById(commentDto.getTargetFeedId()).orElseThrow(
			() -> new IllegalArgumentException("잘못된 피드 아이디 입니다. id=" + commentDto.getTargetFeedId())
		);

		Comment comment = Comment.builder()
			.writer(user)
			.feedId(targetFeed.getFeedId())
			.commentContent(commentDto.getContent())
			.build();

		return commentRepository.save(comment);

	}

	@Transactional
	public Comment updateComment(Long commentId, CommentUpdateRequestDto commentDto) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. id=" + commentId)
		);
		comment.updateContent(commentDto.getContent());
		return comment;

	}

	@Transactional
	public boolean deleteComment(long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. id=" + commentId)
		);
		commentRepository.delete(comment);
		return true;
	}
}
