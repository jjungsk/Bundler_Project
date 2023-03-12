package com.ssafy.bundler.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.bundler.awsS3.FileUploadResponse;
import com.ssafy.bundler.awsS3.S3Uploader;
import com.ssafy.bundler.dto.bundle.request.BundleSaveRequestDto;
import com.ssafy.bundler.dto.bundle.request.BundleUpdateRequestDto;
import com.ssafy.bundler.service.BundleService;
import com.ssafy.bundler.service.CardService;

import lombok.RequiredArgsConstructor;

/**
 * 번들 생성 수정 삭제 컨트롤러
 *
 * @author 이혜지
 * @version 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bundles")
public class BundleController {

	private final BundleService bundleService;
	private final CardService cardService;
	private final S3Uploader s3Uploader;

	//번들을 생성 (빈번들,카드+번들)
//	@PostMapping
//	public ResponseEntity<?> saveBundle(@RequestBody BundleSaveRequestDto requestDto,
//		@RequestParam("bundleThumbnail") MultipartFile multipartFile) throws IOException {
//		// S3 Bucket 내부에 "/bundleThumbnail"
//		FileUploadResponse profile =
//			s3Uploader.uploadBundleThumbnail(requestDto.getUserId(), multipartFile, "bundleThumbnail");
//		requestDto.setBundleThumbnail(profile.getUrl());
//		if (requestDto.getCardSaveRequestDtoList() == null) {
//			bundleService.saveBundle(requestDto);
//		} else {
//			bundleService.saveBundleWithCards(requestDto);
//		}
//		return ResponseEntity.ok("번들 생성 완료.");
//	}

	@PostMapping
	public ResponseEntity<?> saveBundle(@RequestBody BundleSaveRequestDto requestDto) throws IOException {

		if (requestDto.getCardSaveRequestDtoList() == null) {
			bundleService.saveBundle(requestDto);
		} else {
			bundleService.saveBundleWithCards(requestDto);
		}
		return ResponseEntity.ok("번들 생성 완료.");
	}

	//유저 프로필 삭제
	@DeleteMapping("/{user_id}/profilePhoto")
	public ResponseEntity<?> deleteProfilePhoto(@PathVariable("user_id") Long userId) {
		s3Uploader.userFileDelete(userId);

		return ResponseEntity.ok("delete success!");
	}

	@PutMapping("/{feed_id}")
	public ResponseEntity<?> updateBundle(@PathVariable("feed_id") Long feedId,
		@RequestBody BundleUpdateRequestDto requestDto) {

		// s3Uploader.userFileDelete();

		bundleService.updateBundleInfo(feedId, requestDto);

		return ResponseEntity.ok("번들 정보 수정 완료.");
	}

	// @PutMapping("/{feed_id}")
	// public ResponseEntity<?> updateBundle(@PathVariable("feed_id") Long feedId,
	// 	@RequestBody BundleUpdateRequestDto requestDto,
	// 	@RequestParam("bundleThumbnail") MultipartFile multipartFile) {
	//
	// 	// s3Uploader.userFileDelete();
	//
	// 	bundleService.updateBundleInfo(feedId, requestDto);
	//
	// 	return ResponseEntity.ok("번들 정보 수정 완료.");
	// }

	// 번들 삭제 V1
	@DeleteMapping("/v1/{feed_id}")
	public ResponseEntity<?> deleteBundleV1(@PathVariable("feed_id") Long feedId) {
		bundleService.deleteBundleV1(feedId);

		return ResponseEntity.ok("번들 삭제 완료.");
	}

	//번들 삭제 V2
	@DeleteMapping("/{feed_id}")
	public ResponseEntity<?> deleteBundleV2(@PathVariable("feed_id") Long feedId) {
		bundleService.deleteBundleV2(feedId);

		return ResponseEntity.ok("번들 삭제 완료." + feedId);
	}
}
