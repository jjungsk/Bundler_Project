package com.ssafy.bundler.dto.user;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class  UserCalendarResponseDto {
	private Integer year;
	private List<UserCalendarDto> dates;
}
