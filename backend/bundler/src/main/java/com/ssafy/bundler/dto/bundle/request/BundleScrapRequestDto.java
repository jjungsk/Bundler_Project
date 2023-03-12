package com.ssafy.bundler.dto.bundle.request;

import lombok.Data;

@Data
public class BundleScrapRequestDto {

	private Long userId;
	private Long cardId;
	private Long bundleId;

}
