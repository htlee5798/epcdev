package com.lottemart.epc.statistics.controller;

import java.util.Calendar;
import java.util.List;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0009VO;
import com.lottemart.epc.statistics.service.PSCMSTA0009Service;
import com.lottemart.epc.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller

public class PSCMSTA0009Controller {

	@Autowired
	private PSCMSTA0009Service pscmsta0009Service;


	@RequestMapping(value = "/statistics/lotteCardMallObjectCalList.do")
    public String lotteCardMallObjectCalList(@ModelAttribute("searchVO") PSCMSTA0009VO searchVO, ModelMap model) throws Exception {

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

        model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0009";
	}

	@RequestMapping(value = "/statistics/selectLotteCardMallObjectCalList.do")
    public String selectLotteCardMallObjectCalList(@ModelAttribute("searchVO") PSCMSTA0009VO searchVO, ModelMap model) throws Exception {


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
//
		// 데이터 조회
		List<DataMap> list = pscmsta0009Service.selectLotteCardMallObjectCalList(searchVO);
	    model.addAttribute("list", list);

        int totalCount = 0;
        if(list.size() > 0) {
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0009";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/statistics/selectLotteCardMallObjectCalListExcel.do")
	public String selectLotteCardMallObjectCalListExcel(@ModelAttribute("searchVO") PSCMSTA0009VO searchVO, ModelMap model) throws Exception {

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);

		// 데이터 조회
		List<DataMap> list = pscmsta0009Service.selectLotteCardMallObjectCalListExcel(searchVO);
        model.addAttribute("list", list);
		return "statistics/PSCMSTA000901";
	}
}
