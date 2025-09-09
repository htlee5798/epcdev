/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2015. 02. 23. 오후 2:33:50
 * @author      : hippie
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.statistics.model.PSCMSTA0001SearchVO;
import com.lottemart.epc.statistics.service.PSCMSTA0001Service;
import com.lottemart.epc.statistics.service.PSCMSTA0011Service;

/**
 * @Class Name : PSCMSTA0011Controller
 * @Description : 에누리 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.02.23	hippie
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMSTA0011Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0011Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCMSTA0001Service pscmsta0001Service;
	@Autowired
	private PSCMSTA0011Service pscmsta0011Service;

	@Autowired
	private CommonCodeService commonCodeService;


	/**
	 * Desc : 에누리
	 * @Method Name : enuriSummaryList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/statistics/enuriSummaryList.do")
	public String enuriSummaryList(@ModelAttribute("searchVO") PSCMSTA0001SearchVO searchVO, HttpServletRequest request,ModelMap model) throws Exception {

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
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		// 매출유형 조회조건이 없을 경우 최상위 매출유형으로 설정
		if(StringUtil.isNVL(searchVO.getAffiliateLinkNo())) {
			searchVO.setAffiliateLinkNo(searchVO.getRootAffiliateLinkNo());
		}


		model.addAttribute("searchVO", searchVO);

		List<DataMap> affiliateLinkNoList = pscmsta0001Service.selectAffiliateLinkNoList(searchVO);
		model.addAttribute("affiliateLinkNoList", affiliateLinkNoList);

		// 주문유입경로
		List<DataMap> orderPathCdList = commonCodeService.getCodeList("SM137");
		model.addAttribute("orderPathCdList", orderPathCdList);


    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());



        model.addAttribute("paginationInfo", paginationInfo);



		return "statistics/PSCMSTA0011";
	}

	/**
	 * Desc : 에누리 조회 메소드
	 * @Method Name : selectNaverEdmSummaryList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/statistics/selectEnuriSummaryList.do")
	public String selectNaverEdmSummaryList(@ModelAttribute("searchVO") PSCMSTA0001SearchVO searchVO, HttpServletRequest request,ModelMap model) throws Exception {

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
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		// 매출유형 조회조건이 없을 경우 최상위 매출유형으로 설정
		if(StringUtil.isNVL(searchVO.getAffiliateLinkNo())) {
			searchVO.setAffiliateLinkNo(SecureUtil.sqlValid(searchVO.getRootAffiliateLinkNo()));
		}


		model.addAttribute("searchVO", searchVO);

		List<DataMap> affiliateLinkNoList = pscmsta0001Service.selectAffiliateLinkNoList(searchVO);
		model.addAttribute("affiliateLinkNoList", affiliateLinkNoList);

		// 주문유입경로
		List<DataMap> orderPathCdList = commonCodeService.getCodeList("SM137");
		model.addAttribute("orderPathCdList", orderPathCdList);


    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 통계 데이터 조회
		List<DataMap> stats = pscmsta0001Service.selectNaverEdmSummaryTotal(searchVO);
		model.addAttribute("stats", stats.get(0));

		// 데이터 조회
		List<DataMap> list = pscmsta0001Service.selectNaverEdmSummaryList(searchVO);

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

		return "statistics/PSCMSTA0011";
	}



	/**
	 * Desc : 에누리 엑셀조회 메소드
	 * @Method Name : selectNaverEdmSummaryListExcel
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/statistics/selectEnuriSummaryListExcel.do")
	public String selectNaverEdmSummaryListExcel(@ModelAttribute("searchVO") PSCMSTA0001SearchVO searchVO, ModelMap model) throws Exception {

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		// 매출유형 조회조건이 없을 경우 최상위 매출유형으로 설정
		if(StringUtil.isNVL(searchVO.getAffiliateLinkNo())) {
			searchVO.setAffiliateLinkNo(SecureUtil.sqlValid(searchVO.getRootAffiliateLinkNo()));
		}

		model.addAttribute("searchVO", searchVO);

		// 데이터 조회
		List<DataMap> list = pscmsta0001Service.selectNaverEdmSummaryListExcel(searchVO);

		model.addAttribute("list", list);
		return "statistics/PSCMSTA001101";
	}

	/**
	 * 구매상품 팝업 페이지
	 * Desc : 주문번호 클릭시 구매상품 상세정보 팝업
	 * @Method Name : orderProdInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("statistics/enuri/orderProdInfo.do")
	public String orderProdInfo(HttpServletRequest request, Model model) throws Exception {

		// 파라미터
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", request.getParameter("orderId"));

		// 데이터 조회
		List<DataMap> orderItemList = pscmsta0011Service.selectOrderProdItemList(paramMap);
		model.addAttribute("orderItemList", orderItemList);

		return "statistics/PSCMSTA001102";

	}

}
