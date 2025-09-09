package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

public class TedOrdProcess001VO  implements Serializable {

	

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2509030341088101081L;

	/* 업체발주요청마스터 VO */
	private TedPoOrdMstList001VO tedPoOrdMstList001VO = null;
	
	/* 발주정보 리스트 조회 VO */
	private TedOrdList001VO tedOrdList001VO = null;
	
	public TedPoOrdMstList001VO getTedPoOrdMstList001VO() {
		return tedPoOrdMstList001VO;
	}


	public void setTedPoOrdMstList001VO(TedPoOrdMstList001VO tedPoOrdMstList001VO) {
		this.tedPoOrdMstList001VO = tedPoOrdMstList001VO;
	}


	public TedOrdList001VO getTedOrdList001VO() {
		return tedOrdList001VO;
	}


	public void setTedOrdList001VO(TedOrdList001VO tedOrdList001VO) {
		this.tedOrdList001VO = tedOrdList001VO;
	}

	
}
