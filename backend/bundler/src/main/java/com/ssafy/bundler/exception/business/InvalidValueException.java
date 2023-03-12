package com.ssafy.bundler.exception.business;

import com.ssafy.bundler.exception.BusinessException;
import com.ssafy.bundler.exception.ErrorCode;

public class InvalidValueException extends BusinessException {

	public InvalidValueException(String value) {
		super(value, ErrorCode.INVALID_INPUT_VALUE);
	}

	public InvalidValueException(String value, ErrorCode errorCode) {
		super(value, errorCode);
	}

}