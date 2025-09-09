package com.lottemart.epc.edi.payment.service;
import java.util.List;

import com.lottemart.epc.edi.payment.model.NEDMPAY0010VO;


public interface NEDMPAY0010Service {
	/**
	 * 기간별 결산정보  - > 사업자 등록번호
	 * @param NEDMPAY0010VO
	 * @return
	 */
	public List<NEDMPAY0010VO> selectCominforInfo(NEDMPAY0010VO map ) throws Exception;
	
}


