package com.lottemart.epc.edi.order.service;
import java.util.List;

import com.lottemart.epc.edi.order.model.NEDMORD0110VO;


public interface NEDMORD0110Service {
	
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 조회
	 * @param NEDMORD0110VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0110VO> selectOrdAble(NEDMORD0110VO map) throws Exception;
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 수정
	 * @param NEDMORD0110VO
	 * @return
	 * @throws Exception
	 */
	public void updateOrdSplyTime(NEDMORD0110VO map ) throws Exception;
	
}

