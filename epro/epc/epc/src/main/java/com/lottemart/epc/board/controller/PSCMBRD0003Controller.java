/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 4:40:50
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0003SearchVO;
import com.lottemart.epc.board.service.PSCMBRD0003Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;

/** 
 * @Class Name : PSCMBRD0003Controller
 * @Description : 1:1문의목록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후  4:41:50 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMBRD0003Controller
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMBRD0003Controller.class);
	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCMBRD0003Service pscmbrd0003Service;

	/**
	 * Desc : 1:1문의 목록을 조회하는 메소드
	 * @Method Name : inquireList
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectInquireList.do")
	public String inquireList(@ModelAttribute("searchVO") PSCMBRD0003SearchVO searchVO, ModelMap model, HttpServletRequest request) throws Exception
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
	
		request.setAttribute("epcLoginVO", epcLoginVO);
		logger.debug("vendorId ==>" + searchVO.getSearchVendorId() + "<==");
		
		// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
		DataMap paramMap = new DataMap();
		if("".equals(searchVO.getSearchVendorId())) 
		{
			paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); //협력업체코드
		} 
		else 
		{
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add(searchVO.getSearchVendorId());
			paramMap.put("vendorId", vendorList); //협력업체코드
		}
		
		//접수기간
		if (StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate()))
		{
			String startDate = DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday(),-7),"-");
			String endDate   = DateUtil.formatDate(DateUtil.getToday(),"-");
			
			searchVO.setStartDate(startDate); //시작일자
			searchVO.setEndDate(endDate);     //종료일자
			searchVO.setTypeCheck("N");       //처리상태구분

			paramMap.put("startDate",     getOnlyNumber(searchVO.getStartDate())); //시작일자
			paramMap.put("endDate",       getOnlyNumber(searchVO.getEndDate())  ); //종료일자
			paramMap.put("agentLocation", "" 									); //접수위치
			paramMap.put("custQstDivnCd", "" 									); //고객문의구분
			paramMap.put("typeCheck",     searchVO.getTypeCheck() 				); //처리상태구분
		}
		else
		{
			logger.debug("startDate     ==>" + searchVO.getStartDate()     + "<==");
			logger.debug("endDate       ==>" + searchVO.getEndDate()       + "<==");
			logger.debug("agentLocation ==>" + searchVO.getAgentLocation() + "<==");
			logger.debug("custQstDivnCd ==>" + searchVO.getCustQstDivnCd() + "<==");
			logger.debug("typeCheck     ==>" + searchVO.getTypeCheck()     + "<==");

			paramMap.put("startDate",     getOnlyNumber(searchVO.getStartDate())); //시작일자
			paramMap.put("endDate",       getOnlyNumber(searchVO.getEndDate())  ); //종료일자
			paramMap.put("agentLocation", searchVO.getAgentLocation()           ); //접수위치
			paramMap.put("custQstDivnCd", searchVO.getCustQstDivnCd()           ); //고객문의구분
			paramMap.put("typeCheck",     searchVO.getTypeCheck()               ); //처리상태구분
		}

		//접수위치
		List<DataMap> agentLocation = pscmbrd0003Service.agentLocation(searchVO);
		model.addAttribute("agentLocation", agentLocation);
		
		//고객문의구분
		List<DataMap> custQstDivnCd = pscmbrd0003Service.custQstDivnCd(searchVO);
		model.addAttribute("custQstDivnCd", custQstDivnCd);
		model.addAttribute("searchVO", searchVO);
		
		/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		//Query Page Setting
		paramMap.put("recordCountPerPage", searchVO.getRecordCountPerPage());
		paramMap.put("pageIndex",          searchVO.getPageIndex()         );
		
		//총접수건
		List<DataMap> stats = pscmbrd0003Service.selectCounselCount(paramMap);
		model.addAttribute("stats", stats.get(0));
		
		// 데이터 조회
		List<DataMap> list = pscmbrd0003Service.selectinquireList(paramMap);
        model.addAttribute("inquirelist", list);
        
        int totalCount = 0;
        String bflag = "fail";

        if (list.size() > 0) 
        {
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        	logger.debug("totalCount ==>" + totalCount + "<==");
        	bflag = "success";
        }
        else if (list.size() == 0) 
        {
        	bflag = "zero";
        }
        
        request.setAttribute("bflag", bflag);
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);
        
		return "board/PSCMBRD0003";
	}
	
	/** ********************************************
	 * 기호와 숫자가 함께 들어있는 문자열를 받아 숫자만 걸러내어 반환한다
	 ***********************************************/
	public static String getOnlyNumber(String str)
	{
		if(str == null)
		{
			return "";
		}
		
		StringBuffer out = new StringBuffer(512);
		char c;
		int str_length = str.length();
		
		for (int i = 0; i < str_length; i++)
		{
			c = str.charAt(i);
			if('0' <= c && c <= '9')
				out.append(c);
		}
		
		return out.toString();
	}
	
}
