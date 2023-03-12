package com.ssafy.bundler.exception.business;

import com.ssafy.bundler.exception.BusinessException;
import com.ssafy.bundler.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException(String message) {
		super(message, ErrorCode.USER_NOT_FOUND);
	}

	public UserNotFoundException(ErrorCode errorCode) {
		super(ErrorCode.USER_NOT_FOUND);
	}

}