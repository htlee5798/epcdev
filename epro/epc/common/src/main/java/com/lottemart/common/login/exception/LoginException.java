package com.lottemart.common.login.exception;

public class LoginException extends Exception {
	private static final long serialVersionUID = -8330973131231082600L;

	public LoginException(String message) {
		super(message);
	}
	
	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}
}
