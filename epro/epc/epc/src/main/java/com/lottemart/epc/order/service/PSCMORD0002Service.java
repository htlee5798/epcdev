package com.lottemart.epc.order.service;

import java.util.List;


import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0002VO;

public interface PSCMORD0002Service {
	
	public List<DataMap> selectProductSaleSumList(PSCMORD0002VO searchVO) throws Exception;
	
	public List<DataMap> selectProductSaleSumListExcel(PSCMORD0002VO searchVO) throws Exception;
}
