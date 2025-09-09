package com.lottemart.common.api;

import java.io.Serializable;

public class BaseError implements Serializable {
	
	private static final long serialVersionUID = 4074536364816894686L;

	private ErrorCode code;

	private String message;

	public enum ErrorCode {
		FORBIDDEN
		, ILLEGAL_ARGUMENT
		, SERVER_ERROR
	}

	public BaseError(){
		
	}
	
	public BaseError(ErrorCode code, String message){
		this.code = code;
		this.message = message;
	}
	
	public ErrorCode getCode(){
		return code;
	}

	public void setCode(ErrorCode code){
		this.code = code;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}
}
