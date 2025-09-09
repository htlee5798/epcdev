package com.lottemart.epc.order.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.dao.PSCMORD0009Dao;
import com.lottemart.epc.order.model.PSCMORD0009VO;
import com.lottemart.epc.order.service.PSCMORD0009Service;

@Service("PSCMORD0009Service")
public class PSCMORD0009ServiceImpl  implements PSCMORD0009Service {

	@Autowired
	private PSCMORD0009Dao PSCMORD0009Dao;

	public List<DataMap> getTetCodeList(PSCMORD0009VO vo) throws Exception {
		return PSCMORD0009Dao.getTetCodeList(vo);
	}

	public List<DataMap> selectPartnerReturnList(DataMap paramMap) throws Exception {
		return PSCMORD0009Dao.selectPartnerReturnList(paramMap);
	}

}