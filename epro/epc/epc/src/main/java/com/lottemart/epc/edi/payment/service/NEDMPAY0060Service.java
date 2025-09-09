package com.lottemart.epc.edi.payment.service;
import java.util.List;

import com.lottemart.epc.edi.payment.model.NEDMPAY0060VO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0061VO;


public interface NEDMPAY0060Service {
	/**
	 * 기간별 결산정보  - > 구 판매장려금 정보
	 * @param NEDMPAY0060VO
	 * @param request
	 * @return
	 */
	public List<NEDMPAY0060VO> selectPromoSaleInfo(NEDMPAY0060VO map ) throws Exception;
	
	/**
	 * 기간별 결산정보  - > 신 판매장려금 정보
	 * @param NEDMPAY0061VO
	 * @param request
	 * @return
	 */
	public List<NEDMPAY0061VO> selectPromoNewSaleInfo(NEDMPAY0061VO map ) throws Exception;
	
}


