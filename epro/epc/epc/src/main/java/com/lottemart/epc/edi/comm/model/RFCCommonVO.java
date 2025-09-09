package com.lottemart.epc.edi.comm.model;

import java.io.Serializable;

public class RFCCommonVO implements Serializable {

	static final long serialVersionUID = 7120732751650188889L;

	/** PROXY 명 */
	private String proxyNm;
	
	/** json format parameter */
	private String param;
	
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
	
}
