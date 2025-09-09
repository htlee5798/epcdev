package com.lottemart.epc.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.ReviewCondition;
import com.lottemart.epc.board.service.PSCMBRD0015Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : PSCMBRD0015Controller.java
 * @Description : 롯데ON 상품평 관리 Controller 클래스
 * @Modification Information
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019. 01. 09 pjw
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCMBRD0015Controller {

	@Autowired
	private ConfigurationService config;
	@Autowired
	PSCMBRD0015Service pscmbrd0015Service;

	/**
	 * Desc : 롯데ON 상품평 관리 디폴트 페이지
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/ecReviewList.do")
	public String ecReviewForm(HttpServletRequest request) throws Exception {
		pscmbrd0015Service.ecReviewForm(request,config);
		return "board/PSCMBRD0015";
	}
	
	/**
	 * Desc : 롯데ON 상품평 관리 조회
	 * 
	 * @param condition
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/ecReviewSearch.do")
	public @ResponseBody Map<String, Object> ecReviewSearch(@ModelAttribute("condition") ReviewCondition condition, HttpServletRequest request) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = pscmbrd0015Service.ecReviewSearch(condition,request,config);
		return rtnMap;
	}
	
	/**
	 * Desc :  롯데ON 상품평 관리 상세 페이지
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("board/ecReviewDetail.do")
	public String ecReviewDetail(HttpServletRequest request) throws Exception {
		DataMap param = new DataMap(request);
		request.setAttribute("rvDetail", param);
		return "board/PSCPBRD001501";
	}
	
	/**
	 * Desc : 롯데ON 상품평 관리 엑셀다운로드
	 * 
	 * @param condition
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/ecReviewExcelDown.do")
	public @ResponseBody void ecReviewExcelDown(@ModelAttribute("condition") ReviewCondition condition, HttpServletRequest request, HttpServletResponse response) throws Exception {
		pscmbrd0015Service.ecReviewExcelDown(condition,request,response,config);
	}

}
