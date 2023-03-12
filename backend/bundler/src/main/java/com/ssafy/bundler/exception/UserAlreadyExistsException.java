package com.ssafy.bundler.exception;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException() {
		super("이미 회원 가입된 아이디입니다");
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
