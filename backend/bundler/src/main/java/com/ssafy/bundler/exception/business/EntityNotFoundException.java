package com.ssafy.bundler.exception.business;

import com.ssafy.bundler.exception.BusinessException;
import com.ssafy.bundler.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

}