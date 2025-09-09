package com.lottemart.epc.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0007Dao;
import com.lottemart.epc.common.service.PSCMCOM0007Service;
import com.lottemart.epc.common.model.PSCMCOM0007VO;

@Service("PSCMCOM0007Service")
public class PSCMCOM0007ServiceImpl implements PSCMCOM0007Service {
	
	@Autowired
	private PSCMCOM0007Dao PSCMCOM0007Dao;

	public List<DataMap> selectOrderIdList(PSCMCOM0007VO vo) throws Exception {
		return PSCMCOM0007Dao.selectOrderIdList(vo);
	}

}
