package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

public class TedOrdProcess010VO  implements Serializable {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6951587704797357262L;

	//업체발주요청마스터 VO 
	private TedPoOrdMstList010VO tedPoOrdMstList010VO = null;
	
	//발주정보 리스트 조회 VO 
	private TedOrdList010VO tedOrdList010VO = null;
	
	
	
	public TedPoOrdMstList010VO getTedPoOrdMstList010VO() {
		return tedPoOrdMstList010VO;
	}

	public void setTedPoOrdMstList010VO(TedPoOrdMstList010VO tedPoOrdMstList010VO) {
		this.tedPoOrdMstList010VO = tedPoOrdMstList010VO;
	}

	public TedOrdList010VO getTedOrdList010VO() {
		return tedOrdList010VO;
	}

	public void setTedOrdList010VO(TedOrdList010VO tedOrdList010VO) {
		this.tedOrdList010VO = tedOrdList010VO;
	}

	
	
}
