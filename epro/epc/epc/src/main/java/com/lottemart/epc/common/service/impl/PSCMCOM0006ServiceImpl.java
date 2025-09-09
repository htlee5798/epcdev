package com.lottemart.epc.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0006Dao;
import com.lottemart.epc.common.service.PSCMCOM0006Service;
import com.lottemart.epc.common.model.PSCMCOM0006VO;

@Service("PSCMCOM0006Service")
public class PSCMCOM0006ServiceImpl implements PSCMCOM0006Service {
	
	@Autowired
	private PSCMCOM0006Dao PSCMCOM0006Dao;

	public List<DataMap> selectPopupProductList(PSCMCOM0006VO vo) throws Exception {
		return PSCMCOM0006Dao.selectPopupProductList(vo);
	}
	
	public List selectProduct(Map<String, Object> map) throws Exception{
		return PSCMCOM0006Dao.selectProduct(map);
	}
	
	public List<DataMap> selectNorProdDetailList(Map<String, Object> map) throws Exception {
		return PSCMCOM0006Dao.selectNorProdDetailList(map);
	}
	
	public List<DataMap> selectKcProdDetailList(Map<String, Object> map) throws Exception {
		return PSCMCOM0006Dao.selectKcProdDetailList(map);
	}
}
