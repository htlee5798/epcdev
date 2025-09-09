package com.lottemart.epc.substn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lottemart.epc.substn.dao.PSCMSBT0003Dao;
import com.lottemart.epc.substn.service.PSCMSBT0003Service;
import com.lottemart.common.util.DataMap;

/**
 *
 * @Class Name : PSCMSBT0003ServiceImpl
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2015. 11. 25   skc
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMSBT0003Service")
public class PSCMSBT0003ServiceImpl implements PSCMSBT0003Service{

	@Autowired
	private PSCMSBT0003Dao pscmsbt0003Dao;

	/**
	 * 정산내역조회
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectSubStnDeductList(DataMap paramMap) throws Exception {
		return pscmsbt0003Dao.selectSubStnDeductList(paramMap);
	}

	/**
	 * 정산내역집계
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectSubStnDeductSumList(DataMap paramMap) throws Exception {
		return pscmsbt0003Dao.selectSubStnDeductSumList(paramMap);
	}



}