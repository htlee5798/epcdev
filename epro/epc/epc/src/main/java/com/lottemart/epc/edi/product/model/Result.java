package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.HashMap;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2126183754202174350L;

	/* 처리결과(success, error, duplicate) */
	private String resultCode;
	/* 결과 메시지 */
	private String message;
	/* 결과 코드 */
	private String msgCd;
	
	/* 결과값 1 */
	private String rtnValue01;
	
	/* 결과값 1 */
	private String rtnValue02;	
	
	/* 결과 Map*/
	private HashMap<String,Object> resultMap;
	
	/**성공건수*/
	private int completeCnt;
	
	/**실패건수*/
	private int errorCnt;
	
	

	public int getCompleteCnt() {
		return completeCnt;
	}

	public void setCompleteCnt(int completeCnt) {
		this.completeCnt = completeCnt;
	}

	public int getErrorCnt() {
		return errorCnt;
	}

	public void setErrorCnt(int errorCnt) {
		this.errorCnt = errorCnt;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgCd() {
		return msgCd;
	}

	public void setMsgCd(String msgCd) {
		this.msgCd = msgCd;
	}

	public String getRtnValue01() {
		return rtnValue01;
	}

	public void setRtnValue01(String rtnValue01) {
		this.rtnValue01 = rtnValue01;
	}

	public String getRtnValue02() {
		return rtnValue02;
	}

	public void setRtnValue02(String rtnValue02) {
		this.rtnValue02 = rtnValue02;
	}

	public HashMap<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(HashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	
}
