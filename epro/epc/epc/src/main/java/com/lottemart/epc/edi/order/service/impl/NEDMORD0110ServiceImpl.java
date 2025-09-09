package com.lottemart.epc.edi.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.order.dao.NEDMORD0110Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0110VO;
import com.lottemart.epc.edi.order.service.NEDMORD0110Service;

@Service("nedmord0110Service")
public class NEDMORD0110ServiceImpl implements NEDMORD0110Service{
	
	@Autowired
	private NEDMORD0110Dao nedmord0110Dao;
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 조회
	 * @param NEDMORD0110VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0110VO> selectOrdAble(NEDMORD0110VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmord0110Dao.selectOrdAble(map );
	}
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 수정
	 * @param NEDMORD0110VO
	 * @return
	 * @throws Exception
	 */
	public void updateOrdSplyTime(NEDMORD0110VO map) throws Exception {
		NEDMORD0110VO[] updateParam = map.getArrParam();
		for (int i = 0; i < updateParam.length; i++) { 
			nedmord0110Dao.updateOrdSplyTime(updateParam[i]);
		} 
	}
	
	
	

}
