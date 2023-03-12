package com.ssafy.bundler.exception.business;

import com.ssafy.bundler.exception.BusinessException;
import com.ssafy.bundler.exception.ErrorCode;

public class GithubHttpRequestException extends BusinessException {

	public GithubHttpRequestException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public GithubHttpRequestException(ErrorCode errorCode) {
		super(errorCode);
	}

}
