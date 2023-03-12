package com.ssafy.bundler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.domain.Comment;
import com.ssafy.bundler.dto.comment.CommentCreateResponseDto;
import com.ssafy.bundler.dto.comment.CommentSaveRequestDto;
import com.ssafy.bundler.dto.comment.CommentUpdateRequestDto;
import com.ssafy.bundler.service.CommentService;

/**
 * 댓글 작성,수정,삭제 컨트롤러
 *
 * @author 안태윤
 * @version 1.0
 */
@RestController
public class CommentController {
	@Autowired
	CommentService commentService;

	@PostMapping("/api/v1/comment")
	public ResponseEntity<?> saveComment(@RequestBody CommentSaveRequestDto commentDto) {
		Comment comment = commentService.saveComment(commentDto);
		if (comment == null) {
			return ResponseEntity.ok(new CommentCreateResponseDto(false, "댓글 등록에 실패했습니다.",null));
		}
		return ResponseEntity.ok(new CommentCreateResponseDto(true, "댓글이 성공적으로 등록됐습니다.", comment.getCommentId()));
	}

	@PutMapping("/api/v1/comment/{comment_id}")
	public ResponseEntity<?> updateComment(@PathVariable("comment_id") long commentId,
		@RequestBody CommentUpdateRequestDto commentDto) {

		Comment comment = commentService.updateComment(commentId, commentDto);
		if (comment == null) {
			return ResponseEntity.ok(new CommentCreateResponseDto(false, "댓글 수정에 실패했습니다.", null));
		}
		return ResponseEntity.ok(new CommentCreateResponseDto(true, "댓글이 성공적으로 수정됐습니다.", comment.getCommentId()));
	}

	@DeleteMapping("/api/v1/comment/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable("comment_id") long commentId) {
		boolean result = commentService.deleteComment(commentId);
		if (!result) {
			return ResponseEntity.ok(new CommentCreateResponseDto(false, "댓글 삭제에 실패했습니다.",commentId));
		}
		return ResponseEntity.ok(new CommentCreateResponseDto(false, "댓글이 삭제 됐습니다.",commentId));
	}
}
