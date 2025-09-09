package com.lottemart.epc.substn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lottemart.epc.substn.dao.PSCMSBT0001Dao;
import com.lottemart.epc.substn.service.PSCMSBT0001Service;
import com.lottemart.common.util.DataMap;

/**
 *
 * @Class Name : PSCMSBT0001ServiceImpl
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
@Service("PSCMSBT0001Service")
public class PSCMSBT0001ServiceImpl implements PSCMSBT0001Service{

	@Autowired
	private PSCMSBT0001Dao pscmsbt0001Dao;

	/**
	 * 업체별 매출공제 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPaySubStnList(DataMap paramMap) throws Exception {
		return pscmsbt0001Dao.selectPaySubStnList(paramMap);
	}



}