package com.lottemart.epc.edi.buy.model;

import java.io.Serializable;

public class NEDMBUY0110VO implements Serializable {

	static final long serialVersionUID = -6923502002735541844L;

	/** RFC PROXY 명 */
	private String proxyNm;
	
	/** json format parameter */
	private String param;
	
	/** TextData 검색조건명 */
	private String textData;
	
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
