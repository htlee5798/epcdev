package com.lottemart.epc.edi.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.order.dao.NEDMORD0120Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0120VO;
import com.lottemart.epc.edi.order.service.NEDMORD0120Service;

@Service("nedmord0120Service")
public class NEDMORD0120ServiceImpl implements NEDMORD0120Service{
	
	@Autowired
	private NEDMORD0120Dao nedmord0120Dao;
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 조회
	 * @param NEDMORD0120VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0120VO> selectOrdSply(NEDMORD0120VO map) throws Exception{
		// TODO Auto-generated method stub
		
		return nedmord0120Dao.selectOrdSply(map );
	}
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 변경
	 * @param NEDMORD0120VO
	 * @return
	 * @throws Exception
	 */
	public void updateOrdSply(NEDMORD0120VO map) throws Exception {
		NEDMORD0120VO[] updateParam = map.getArrParam();
		for (int i = 0; i < updateParam.length; i++) { 
			nedmord0120Dao.updateOrdSply(updateParam[i]);
		} 
	}
	
	

}
