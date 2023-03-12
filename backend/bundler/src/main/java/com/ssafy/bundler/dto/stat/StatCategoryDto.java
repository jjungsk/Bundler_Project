package com.ssafy.bundler.dto.stat;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatCategoryDto {
	private double proportion;
	private Long categoryMakeCount;
	private String categoryName;
	private List<StatCategoryDto> subCategories;

}
