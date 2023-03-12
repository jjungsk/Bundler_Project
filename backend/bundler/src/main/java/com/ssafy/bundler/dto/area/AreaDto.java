package com.ssafy.bundler.dto.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AreaDto {

	private Long userId;
	private String[] job;
	private String[] skill;

}
