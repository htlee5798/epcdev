package com.lottemart.epc.edi.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.dao.NEDMPRO0070Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0070VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0070Service;

@Service("NEDMPRO0070Service")
public class NEDMPRO0070ServiceImpl implements NEDMPRO0070Service{
	
	@Resource(name="NEDMPRO0070Dao")
	private NEDMPRO0070Dao NEDMPRO0070Dao;

	@Override
	public List<NEDMPRO0070VO> getCode(DataMap dataMap)  throws Exception{
		return NEDMPRO0070Dao.getCode(dataMap);
	}

	@Override
	public List<NEDMPRO0070VO> getKc(DataMap dataMap) throws Exception {
		return NEDMPRO0070Dao.getKc(dataMap);
	}

	@Override
	public List<NEDMPRO0070VO> getBo(DataMap dataMap) throws Exception {
		return NEDMPRO0070Dao.getBo(dataMap);
	}

	@Override
	public List<NEDMPRO0070VO> getPl(DataMap dataMap) throws Exception {
		return NEDMPRO0070Dao.getPl(dataMap);
	}

}
