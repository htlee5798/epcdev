package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

public class EdiRtnProdProcessVO  implements Serializable {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5133496269883268451L;

	//업체반품 삭제 요청 목록 정보 
	private EdiRtnProdListVO ediRtnProdListVO = null;
	
	//업체반품 삭제 요청 마스터정보
	private EdiRtnProdVO	 ediRtnProdVO	= null;
	
	
	public EdiRtnProdListVO getEdiRtnProdListVO() {
		return ediRtnProdListVO;
	}

	public void setEdiRtnProdListVO(EdiRtnProdListVO ediRtnProdListVO) {
		this.ediRtnProdListVO = ediRtnProdListVO;
	}

	public EdiRtnProdVO getEdiRtnProdVO() {
		return ediRtnProdVO;
	}

	public void setEdiRtnProdVO(EdiRtnProdVO ediRtnProdVO) {
		this.ediRtnProdVO = ediRtnProdVO;
	}
	
	
	
	
	
	
	
	
	
}
