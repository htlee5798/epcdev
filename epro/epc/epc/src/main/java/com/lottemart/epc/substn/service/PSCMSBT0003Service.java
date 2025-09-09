package com.lottemart.epc.substn.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;

/**
 *  
 * @Class Name : PSCMSBT0003Service
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
public interface PSCMSBT0003Service {

	/**
	 * 정산내역조회
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectSubStnDeductList(DataMap paramMap) throws Exception;
	/**
	 * 정산내역집계
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectSubStnDeductSumList(DataMap paramMap) throws Exception;	
	
}