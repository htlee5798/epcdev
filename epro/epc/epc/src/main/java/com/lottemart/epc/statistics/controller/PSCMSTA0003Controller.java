package com.lottemart.epc.statistics.controller;

import java.util.Calendar;
import java.util.List;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.StringUtil;
//import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0003VO;
import com.lottemart.epc.statistics.service.PSCMSTA0003Service;
import com.lottemart.epc.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMSTA0003Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0003Controller.class);

	@Autowired
	private PSCMSTA0003Service pscmsta0003Service;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "/statistics/asianaBalanceAccountList.do")
	public String asianaBalanceAccountList(@ModelAttribute("searchVO") PSCMSTA0003VO searchVO, ModelMap model) throws Exception {

		if(StringUtil.isNVL(searchVO.getSearchUseYn())) {
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
			searchVO.setSearchGbn("1");
		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		logger.debug("from 주문일 or 결제일   ===============" + searchVO.getStartDate());
		logger.debug("to 주문일 or 결제일        ===============" + searchVO.getEndDate());
		logger.debug("주문번호 		  ===============" + searchVO.getOrderId());
		logger.debug("회원명                            ===============" + searchVO.getCustNm());
		logger.debug("아시아나 회원번호  ===============" + searchVO.getaMemberNo());

		model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0003";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "/statistics/selectAsianaBalanceAccountList.do")
	public String selectAsianaBalanceAccountList(@ModelAttribute("searchVO") PSCMSTA0003VO searchVO, ModelMap model) throws Exception {

		if(StringUtil.isNVL(searchVO.getSearchUseYn())) {
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
			searchVO.setSearchGbn("1");
		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		logger.debug("from 주문일 or 결제일   ===============" + searchVO.getStartDate());
		logger.debug("to 주문일 or 결제일        ===============" + searchVO.getEndDate());
		logger.debug("주문번호 		  ===============" + searchVO.getOrderId());
		logger.debug("회원명                            ===============" + searchVO.getCustNm());
		logger.debug("아시아나 회원번호  ===============" + searchVO.getaMemberNo());

		// 데이터 조회
		List<DataMap> list = pscmsta0003Service.selectAsianaBalanceAccountList(searchVO);

		DataMap sum = pscmsta0003Service.selectAsianaBalanceAccountSum(searchVO);
        model.addAttribute("list", list);
        model.addAttribute("sum", sum);

        int totalCount = 0;
        if(list.size() > 0) {
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0003";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "/statistics/selectAsianaBalanceAccountListExcel.do")
	public String selectAsianaBalanceAccountListExcel(@ModelAttribute("searchVO") PSCMSTA0003VO searchVO, ModelMap model) throws Exception {

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);

		logger.debug("from 주문일 or 결제일   ===============" + searchVO.getStartDate());
		logger.debug("to 주문일 or 결제일        ===============" + searchVO.getEndDate());
		logger.debug("주문번호 		  ===============" + searchVO.getOrderId());
		logger.debug("회원명                            ===============" + searchVO.getCustNm());
		logger.debug("아시아나 회원번호  ===============" + searchVO.getaMemberNo());

		// 데이터 조회
		List<DataMap> list = pscmsta0003Service.selectAsianaBalanceAccountListExcel(searchVO);
        model.addAttribute("list", list);
		return "statistics/PSCMSTA000301";
	}	
	
}

