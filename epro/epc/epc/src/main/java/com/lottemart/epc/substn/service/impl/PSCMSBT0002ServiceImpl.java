package com.lottemart.epc.substn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lottemart.epc.substn.dao.PSCMSBT0002Dao;
import com.lottemart.epc.substn.service.PSCMSBT0002Service;
import com.lottemart.common.util.DataMap;

/**
 *
 * @Class Name : PSCMSBT0002ServiceImpl
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
@Service("PSCMSBT0002Service")
public class PSCMSBT0002ServiceImpl implements PSCMSBT0002Service{

	@Autowired
	private PSCMSBT0002Dao pscmsbt0002Dao;

	/**
	 * 업체별 매출공제 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectVendorSubStnList(DataMap paramMap) throws Exception {
		return pscmsbt0002Dao.selectVendorSubStnList(paramMap);
	}

	/**
	 * 업체별 매출상세 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectProdSubStnList(DataMap paramMap) throws Exception {
		return pscmsbt0002Dao.selectProdSubStnList(paramMap);
	}



}