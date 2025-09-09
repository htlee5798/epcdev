package com.lottemart.epc.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.dao.PSCMORD0002Dao;
import com.lottemart.epc.order.model.PSCMORD0002VO;
import com.lottemart.epc.order.service.PSCMORD0002Service;

@Service("PSCMORD0002Service")
public class PSCMORD0002ServiceImpl implements PSCMORD0002Service {

	@Autowired
	private PSCMORD0002Dao pscmord0002Dao;

	public List<DataMap> selectProductSaleSumList(PSCMORD0002VO searchVO) throws Exception {
		return pscmord0002Dao.selectProductSaleSumList(searchVO);
	}

	public List<DataMap> selectProductSaleSumListExcel(PSCMORD0002VO searchVO) throws Exception {
		return pscmord0002Dao.selectProductSaleSumListExcel(searchVO);
	}

}