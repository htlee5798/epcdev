package com.lottemart.epc.edi.consult.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.NEDMCST0230Dao;
import com.lottemart.epc.edi.consult.model.NEDMCST0230VO;
import com.lottemart.epc.edi.consult.service.NEDMCST0230Service;
import com.lottemart.epc.util.Utils;

@Service("nedmcst0230Service")
public class NEDMCST0230ServiceImpl implements NEDMCST0230Service{
	
	@Autowired
	private NEDMCST0230Dao nedmcst0230Dao;
	
	/*
	public List<NEDMCST0230VO> asMainSelect(NEDMCST0230VO nEDMCST0230VO) throws Exception{
		return nedmcst0230Dao.asMainSelect(nEDMCST0230VO );
	}
	*/
	
	public List<NEDMCST0230VO> asMainSelect(NEDMCST0230VO nEDMCST0230VO) throws Exception{
		
		List<NEDMCST0230VO> resultList = nedmcst0230Dao.asMainSelect(nEDMCST0230VO );
		
		for(NEDMCST0230VO result : resultList){
			
			// Masking
			result.setCustNm(Utils.getMaskingName(result.getCustNm()));
			//result.setHpNo(Utils.getMaskingTelNo(result.getHpNo())); 전화번호는 마스킹 해제
			result.setAddr1(Utils.getMaskingAddr(result.getAddr1()));
			result.setNewAddr1(Utils.getMaskingAddr(result.getNewAddr1()));
			
			resultList.set(resultList.indexOf(result), result);
		}
		
		return resultList;
	}

	
	@Override
	public int asMainSelectCount(NEDMCST0230VO nEDMCST0230VO) throws Exception {
		return nedmcst0230Dao.asMainSelectCount(nEDMCST0230VO );
	}

	@Override
	public int asMainUpdate(NEDMCST0230VO nEDMCST0230VO) throws Exception {
		return nedmcst0230Dao.asMainUpdate(nEDMCST0230VO );
	}
	
}
