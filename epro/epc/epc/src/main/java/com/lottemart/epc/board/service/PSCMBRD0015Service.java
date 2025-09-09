package com.lottemart.epc.board.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.board.model.ReviewCondition;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : PSCPBRD0015Service.java
 * @Description : 롯데ON 상품평 관리 조회 Service 클래스
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019. 01. 09.  pjw
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCMBRD0015Service {
	
	public void ecReviewForm(HttpServletRequest request, ConfigurationService config) throws Exception;
	
	public Map<String, Object> ecReviewSearch(ReviewCondition condition, HttpServletRequest request, ConfigurationService config);

	public void ecReviewExcelDown(ReviewCondition condition, HttpServletRequest request, HttpServletResponse response, ConfigurationService config);

}
