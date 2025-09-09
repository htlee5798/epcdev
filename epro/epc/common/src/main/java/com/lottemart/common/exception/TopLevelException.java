package com.lottemart.common.exception;

public class TopLevelException extends Exception {
	private static final long serialVersionUID = 6234733708104436469L;

	public TopLevelException(String message) {
		super(message);
	}
	
	public TopLevelException(String message, Throwable cause) {
		super(message, cause);
	}
}
