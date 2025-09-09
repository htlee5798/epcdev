package com.lottemart.epc.edi.payment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.payment.dao.NEDMPAY0010Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0010VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0010Service;

@Service("nedmpay0010Service")
public class NEDMPAY0010ServiceImpl implements NEDMPAY0010Service{
	
	@Autowired
	private NEDMPAY0010Dao nedmpay0010Dao;
	
	/**
	 * 기간별 결산정보  - > 사업자 등록번호
	 * @param NEDMPAY0010VO
	 * @return
	 */
	public List<NEDMPAY0010VO> selectCominforInfo(NEDMPAY0010VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0010Dao.selectCominforInfo(map );
	}
	

}

