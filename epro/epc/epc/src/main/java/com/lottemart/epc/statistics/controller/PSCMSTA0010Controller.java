package com.lottemart.epc.statistics.controller;

import java.util.Calendar;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.statistics.model.PSCMSTA0010VO;
import com.lottemart.epc.statistics.service.PSCMSTA0010Service;
import com.lottemart.epc.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMSTA0010Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0010Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMSTA0010Service pscmsta0010Service;








	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/statistics/recipeContributionEdmList.do")
	public String recipeContributionEdmList(@ModelAttribute("searchVO") PSCMSTA0010VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		//selectRecipeConEdmSummaryTotal
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

		HashMap<String,String> paramMap = new HashMap<String, String>();

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		paramMap.put("fromDate", searchVO.getStartDate().replaceAll("-", "") );
		paramMap.put("toDate", searchVO.getEndDate().replaceAll("-", ""));

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

		return "statistics/PSCMSTA0010";

	}


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/statistics/selectRecipeContributionEdmList.do")
	public String selectRecipeCon(@ModelAttribute("searchVO") PSCMSTA0010VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		//selectRecipeConEdmSummaryTotal
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

		HashMap<String,String> paramMap = new HashMap<String, String>();

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		paramMap.put("fromDate", searchVO.getStartDate().replaceAll("-", "") );
		paramMap.put("toDate", searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 통계 데이터 조회
		DataMap stats = pscmsta0010Service.selectRecipeConEdmSummaryTotal(paramMap) ;
    	// 데이터 조회
		List<DataMap> list = pscmsta0010Service.selectRecipeConEdmSummaryList(searchVO);


		String realAmt = stats.getString("REAL_AMT");
		String reWord = "";
		if(!"".equals(realAmt)){
			double d_realAmt = Double.valueOf(realAmt) * 0.01;
			reWord = String.valueOf(Math.round(d_realAmt));
		}
		stats.put("REWORD", reWord);

		request.setAttribute("stats", stats);

		model.addAttribute("list", list);

        int totalCount = 0;
        if(list.size() > 0) {
       	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0010";

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/statistics/selectRecipeContributionEdmListExecl.do")
	public String selectRecipeContributionEdmListExecl(@ModelAttribute("searchVO") PSCMSTA0010VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));
		searchVO.setAffiliateLinkNo("00160001");
		model.addAttribute("searchVO", searchVO);

		// 데이터 조회
		List<DataMap> list = pscmsta0010Service.selectRecipeConEdmSummaryListExcel(searchVO);
        model.addAttribute("list", list);
		return "statistics/PSCMSTA001001";
	}
}
