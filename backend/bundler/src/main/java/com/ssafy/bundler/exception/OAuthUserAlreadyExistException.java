package com.ssafy.bundler.exception;

public class OAuthUserAlreadyExistException extends BusinessException {
	public OAuthUserAlreadyExistException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public OAuthUserAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}
}
