package com.ssafy.bundler.controller.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *packageName    : com.ssafy.bundler.controller.common
 * fileName       : ApiResponse
 * author         : modsiw
 * date           : 2023/02/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/06        modsiw       최초 생성
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
	private T data;
}
