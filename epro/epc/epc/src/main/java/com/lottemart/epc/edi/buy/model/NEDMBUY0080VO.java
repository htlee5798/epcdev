package com.lottemart.epc.edi.buy.model;

import java.io.Serializable;
/**
 * @Class Name : NEDMBUY0080VO
 * @Description : 매입정보 증점품확정 조회 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2016.05.31	SONG MIN KYO	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */
public class NEDMBUY0080VO implements Serializable {
	
	private static final long serialVersionUID = 4433860211126164933L;

	/* RFC CALL 정보 & Txt파일 생성을 위해 선언 */
	private String proxyNm;		//RFC CALL Proxy명
	private String param;		//RFC CALL param
	private String textData;	//Txt파일에 보여줄 검색조건
	
	public String getProxyNm() {
		return proxyNm;
	}
	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getTextData() {
		return textData;
	}
	public void setTextData(String textData) {
		this.textData = textData;
	}
	
	
	
}

