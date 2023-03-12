package com.ssafy.bundler.exception;

public class LoginFailedException extends BusinessException {

	public LoginFailedException(String value) { // value는 백에서만 보이는 메시지
		super(value, ErrorCode.LOGIN_INPUT_INVALID);
	}

	public LoginFailedException(String value, ErrorCode errorCode) {
		super(value, errorCode);
	}

}