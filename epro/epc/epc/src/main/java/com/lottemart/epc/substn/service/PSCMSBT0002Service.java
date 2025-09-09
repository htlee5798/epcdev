package com.lottemart.epc.substn.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;

/**
 *  
 * @Class Name : PSCMSBT0002Service
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2015. 11. 25   skc
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMSBT0002Service {

	/**
	 * 업체별 매출공제 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectVendorSubStnList(DataMap paramMap) throws Exception;
	
	/**
	 * 업체별 매출상세 목록
	 * @return DataMap
	 * @throws Exception
	 */	
	public List<DataMap> selectProdSubStnList(DataMap paramMap) throws Exception;
	
	
	
}