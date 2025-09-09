/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.statistics.model.PSCMSTA0004SearchVO;
import com.lottemart.epc.statistics.service.PSCMSTA0004Service;

/**
 * Handles requests for the application home page.
 */
/**
 * @Class Name : PSCMSTA0004Controller
 * @Description : 아시아나정산관리 목록 조회 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:31:49 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMSTA0004Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0004Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCMSTA0004Service pscmsta0004Service;

	/**
	 * Desc : 아시아나정산관리
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "/statistics/asianaMileageList.do")
	public String asianaMileageList(@ModelAttribute("searchVO") PSCMSTA0004SearchVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		if(StringUtil.isNVL(searchVO.getSearchMonth())) {
			return "statistics/PSCMSTA0004";
		}

		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		String searchMonth = searchVO.getSearchMonth();
		String startDate = searchMonth + "01";
		String endDate = searchMonth + getLastDayOfMonth(searchMonth.substring(0, 4), searchMonth.substring(4, 6));
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);

        model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0004";
	}


	/**
	 * Desc : 아시아나정산관리 목록 조회 메소드
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "/statistics/selectAsianaMileageList.do")
	public String selectAsianaMileageList(@ModelAttribute("searchVO") PSCMSTA0004SearchVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		if(StringUtil.isNVL(searchVO.getSearchMonth())) {
			return "statistics/PSCMSTA0004";
		}

		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		String searchMonth = searchVO.getSearchMonth();
		String startDate = searchMonth + "01";
		String endDate = searchMonth + getLastDayOfMonth(searchMonth.substring(0, 4), searchMonth.substring(4, 6));
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);

		// 데이터 조회
		List<DataMap> list = pscmsta0004Service.selectAsianaMileageList(searchVO);

		DataMap sum = pscmsta0004Service.selectAsianaMileageSum(searchVO);
        model.addAttribute("list", list);
        model.addAttribute("sum", sum);

        int totalCount = 0;
        String resultMsg = "해당 자료가 없습니다.";
        if(list.size() > 0) {
        	resultMsg = "정상적으로 조회되었습니다.";
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);

        model.addAttribute("resultMsg", resultMsg);

		return "statistics/PSCMSTA0004";
	}

  	public static int getLastDayOfMonth(String year, String month)	{
		Calendar nextMonthFirstDay = Calendar.getInstance();
		nextMonthFirstDay.set(Integer.parseInt(year), Integer.parseInt(month), 1);

		long l = nextMonthFirstDay.getTime().getTime() - 86400000;

		Calendar currentMonthLastDay = Calendar.getInstance();
		currentMonthLastDay.setTime( new Date(l) );

		return currentMonthLastDay.get(Calendar.DAY_OF_MONTH);
	}

	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "/statistics/selectAsianaMileageListExcel.do")
	public String selectAsianaMileageListExcel(@ModelAttribute("searchVO") PSCMSTA0004SearchVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		model.addAttribute("searchVO", searchVO);
		String searchMonth = searchVO.getSearchMonth();
		String startDate = searchMonth + "01";
		String endDate = searchMonth + getLastDayOfMonth(searchMonth.substring(0, 4), searchMonth.substring(4, 6));
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);

		// 데이터 조회
		List<DataMap> list = pscmsta0004Service.selectAsianaMileageListExcel(searchVO);
        model.addAttribute("list", list);
		return "statistics/PSCMSTA000401";
	}

}
