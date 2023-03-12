package com.ssafy.bundler.dto.feed;

import lombok.Data;

/**
 *packageName    : com.ssafy.bundler.dto.feed
 * fileName       : UserBundleListSummary
 * author         : modsiw
 * date           : 2023/02/08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/08        modsiw       최초 생성
 */
@Data
public class UserBundleListSummary {
	private Long bundleId;
	private String feedTitle;
	private boolean isAbleToInsertCardInBundle;

	public UserBundleListSummary(Long bundleId, String feedTitle) {
		this.bundleId = bundleId;
		this.feedTitle = feedTitle;
	}
}
