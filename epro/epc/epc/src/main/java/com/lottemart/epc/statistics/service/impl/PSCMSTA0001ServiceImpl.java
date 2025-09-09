/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0001Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0001SearchVO;
import com.lottemart.epc.statistics.service.PSCMSTA0001Service;

/**
 * @Class Name : PSCMSTA0001ServiceImpl
 * @Description : 네이버지식쇼핑/쇼핑캐스트 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:25:39 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMSTA0001Service")
public class PSCMSTA0001ServiceImpl implements PSCMSTA0001Service {
	
	@Autowired
	private PSCMSTA0001Dao pscmsta0001Dao;

	/**
	 * Desc : 매출유형 조회 메소드
	 * @Method Name : selectAffiliateLinkNoList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0001SearchVO searchVO) throws Exception {
		return pscmsta0001Dao.selectAffiliateLinkNoList(searchVO);
	}

	/**
	 * Desc : 네이버지식쇼핑/쇼핑캐스트 통계 조회 메소드
	 * @Method Name : selectNaverEdmSummaryTotal
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectNaverEdmSummaryTotal(PSCMSTA0001SearchVO searchVO) throws Exception {
		return pscmsta0001Dao.selectNaverEdmSummaryTotal(searchVO);
	}

	/**
	 * Desc : 네이버지식쇼핑/쇼핑캐스트 목록 조회 메소드
	 * @Method Name : selectNaverEdmSummaryList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectNaverEdmSummaryList(PSCMSTA0001SearchVO searchVO) throws Exception {
		return pscmsta0001Dao.selectNaverEdmSummaryList(searchVO);
	}
	
	/**
	 * Desc : 네이버지식쇼핑/쇼핑캐스트 목록 엑셀조회 메소드
	 * @Method Name : selectNaverEdmSummaryListExcel
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectNaverEdmSummaryListExcel(PSCMSTA0001SearchVO searchVO) throws Exception {
		return pscmsta0001Dao.selectNaverEdmSummaryListExcel(searchVO);
	}
}
