/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.statistics.model.PSCMSTA0008SearchVO;
import com.lottemart.epc.statistics.service.PSCMSTA0008Service;
import com.lottemart.epc.util.Utils;


/**
 * Handles requests for the application home page.
 */
/**
 * @Class Name : PSCMSTA0008Controller
 * @Description : 롯데 목록 조회 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2012. 08.28. 오후 5:31:49 leedb
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller

public class PSCMSTA0008Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0008Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCMSTA0008Service pscmsta0008Service;





	/**
	 * Desc : 롯데카드몰정산관리
	 * @Method Name : lotteCardMallCalList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */

	@RequestMapping(value = "/statistics/lotteCardMallCalList.do")
	public String lotteCardMallCalList(@ModelAttribute("searchVO") PSCMSTA0008SearchVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			String date_from = "";     // 기간(시작)
			String date_to 	 = "";     // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
			// 초최 오픈시 Default 값 세팅
			if(date_from == null || date_from.equals("")) date_from = today_date2;
			if(date_to   == null || date_to.equals(""))   date_to   = today_date;

			searchVO.setStartDate(date_from);
			searchVO.setEndDate(date_to);

		}

		HashMap paramMap = new HashMap<String, String>();

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		paramMap.put("fromDate", searchVO.getStartDate().replaceAll("-", "") );
		paramMap.put("toDate", searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);


		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        model.addAttribute("paginationInfo", paginationInfo);


		return "statistics/PSCMSTA0008";
	}





	/**
	 * Desc : 롯데  목록 조회 메소드
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */

	@RequestMapping(value = "/statistics/selectLotteCardMallCalList.do")
	public String selectLotteCardMall(@ModelAttribute("searchVO") PSCMSTA0008SearchVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			String date_from = "";     // 기간(시작)
			String date_to 	 = "";     // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
			// 초최 오픈시 Default 값 세팅
			if(date_from == null || date_from.equals("")) date_from = today_date2;
			if(date_to   == null || date_to.equals(""))   date_to   = today_date;

			searchVO.setStartDate(date_from);
			searchVO.setEndDate(date_to);

		}

		HashMap paramMap = new HashMap<String, String>();

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		paramMap.put("fromDate", searchVO.getStartDate().replaceAll("-", "") );
		paramMap.put("toDate", searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);


		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 데이터 조회

		List<DataMap> list = pscmsta0008Service.selectLotteCardMallCalList(searchVO);


        model.addAttribute("list", list);

        int totalCount = 0;
        String resultMsg = "해당 자료가 없습니다.";
        if(list.size() > 0) {
        	resultMsg = "정상적으로 조회되었습니다.";
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);

        model.addAttribute("resultMsg", resultMsg);
		return "statistics/PSCMSTA0008";
	}

  	public static int getLastDayOfMonth(String year, String month)	{
		Calendar nextMonthFirstDay = Calendar.getInstance();
		nextMonthFirstDay.set(Integer.parseInt(year), Integer.parseInt(month), 1);

		long l = nextMonthFirstDay.getTime().getTime() - 86400000;

		Calendar currentMonthLastDay = Calendar.getInstance();
		currentMonthLastDay.setTime( new Date(l) );

		return currentMonthLastDay.get(Calendar.DAY_OF_MONTH);
  	}

	@RequestMapping(value = "/statistics/selectLotteCardMallCalListExcel.do")
	public String selectAsianaMileageListExcel(@ModelAttribute("searchVO") PSCMSTA0008SearchVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

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

		HashMap<String,String> paramMap = new HashMap<String, String>();

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		paramMap.put("fromDate", searchVO.getStartDate().replaceAll("-", "") );
		paramMap.put("toDate", searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);

		// 데이터 조회selectLotteCardMallCalListExcel
		List<DataMap> list = pscmsta0008Service.selectLotteCardMallCalListExcel(searchVO);
        model.addAttribute("list", list);
		return "statistics/PSCMSTA000801";
	}
}
