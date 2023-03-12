package com.ssafy.bundler.dto.bundle.request;

import lombok.Data;

@Data
public class BundleUpdateRequestDto {

	// private Long feedId;
	private String feedTitle;
	private String feedContent;
	private String bundleThumbnail;
	private String bundleThumbnailText;
	private boolean isBundlePrivate;

}
