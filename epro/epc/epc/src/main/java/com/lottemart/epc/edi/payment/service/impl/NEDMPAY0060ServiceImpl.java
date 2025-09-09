package com.lottemart.epc.edi.payment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.payment.dao.NEDMPAY0060Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0060VO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0061VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0060Service;

@Service("nedmpay0060Service")
public class NEDMPAY0060ServiceImpl implements NEDMPAY0060Service{
	
	@Autowired
	private NEDMPAY0060Dao nedmpay0060Dao;
	
	/**
	 * 기간별 결산정보  - > 구 판매장려금 정보
	 * @param NEDMPAY0060VO
	 * @param request
	 * @return
	 */
	public List<NEDMPAY0060VO> selectPromoSaleInfo(NEDMPAY0060VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0060Dao.selectPromoSaleInfo(map);
	}
	
	/**
	 * 기간별 결산정보  - > 신 판매장려금 정보
	 * @param NEDMPAY0061VO
	 * @param request
	 * @return
	 */
	public List<NEDMPAY0061VO> selectPromoNewSaleInfo(NEDMPAY0061VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0060Dao.selectPromoNewSaleInfo(map);
	}

}

