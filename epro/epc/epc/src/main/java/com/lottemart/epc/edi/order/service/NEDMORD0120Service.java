package com.lottemart.epc.edi.order.service;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.order.model.NEDMORD0110VO;
import com.lottemart.epc.edi.order.model.NEDMORD0120VO;


public interface NEDMORD0120Service {
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 조회
	 * @param NEDMORD0120VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0120VO> selectOrdSply(NEDMORD0120VO map ) throws Exception;
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 변경
	 * @param NEDMORD0120VO
	 * @return
	 * @throws Exception
	 */
	public void updateOrdSply(NEDMORD0120VO map ) throws Exception;
	
}

