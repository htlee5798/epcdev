package com.lottemart.epc.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0050Service
 * @Description : 증정품관리 Service 클래스
 * @Modification Information
 * 
 * << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.06.07   projectBOS32	신규생성      
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMPRD0050Service {

	/**
	 * Desc : 증정품관리 조회하는 메소드
	 * @Method Name : selectComponentList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectGiftList(DataMap paramMap) throws Exception;

	/**
	 * Desc : 증정품관리 저장하는 메소드
	 * @Method Name : updateBatchGift
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateBatchGift(HttpServletRequest request) throws Exception;
}
