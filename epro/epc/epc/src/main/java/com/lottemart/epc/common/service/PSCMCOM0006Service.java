package com.lottemart.epc.common.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0006VO;

public interface PSCMCOM0006Service {

	public List<DataMap> selectPopupProductList(PSCMCOM0006VO vo) throws Exception;
	
	public List selectProduct(Map<String, Object> map) throws Exception;
	
	public List<DataMap> selectNorProdDetailList(Map<String, Object> map) throws Exception;
	
	public List<DataMap> selectKcProdDetailList(Map<String, Object> map) throws Exception;
}
