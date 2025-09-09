/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.controller;

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

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.statistics.model.PSCMSTA0001SearchVO;
import com.lottemart.epc.statistics.service.PSCMSTA0001Service;

/**
 * @Class Name : PSCMSTA0017Controller
 * @Description : 위메프 / 위메프CPS Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2017. 12. 27. 오후 5:25:35 sskim
 *
 * @Copyright (C) 2000 ~ 2017 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMSTA0017Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0017Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCMSTA0001Service pscmsta0001Service;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * Desc : 위메프 메소드
	 * @Method Name : selectNaverEdmSummaryList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/statistics/weMakePriceEdmSummaryList.do")
	public String weMakePriceEdmSummaryList(@ModelAttribute("searchVO") PSCMSTA0001SearchVO searchVO, HttpServletRequest request,ModelMap model) throws Exception {

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
		if(affiliateLinkNoList.size() > 0){
			ArrayList<String> knowshopping =  new ArrayList<String>();
			for(int a = 0 ; affiliateLinkNoList.size() > a ; a++){
				DataMap affiliateLink = affiliateLinkNoList.get(a);
				String AFFILIATE_LINK_NM = affiliateLink.getString("AFFILIATE_LINK_NM");
				String AFFILIATE_LINK_NO = affiliateLink.getString("AFFILIATE_LINK_NO");
				String UP_AFFILIATE_LINK_NO = affiliateLink.getString("UP_AFFILIATE_LINK_NO");
				String DEPTH = affiliateLink.getString("DEPTH");
				String LEAF_YN = affiliateLink.getString("LEAF_YN");
				if(!AFFILIATE_LINK_NO.equals("0156")){
					knowshopping.add(AFFILIATE_LINK_NO);
				}
			}
			if(knowshopping.size() > 0){
				DataMap paramMap = new DataMap();
				paramMap.put("AFFILIATE_LINK_NM", "원더쇼핑 합계");
				paramMap.put("AFFILIATE_LINK_NO", knowshopping);
				paramMap.put("UP_AFFILIATE_LINK_NO", "0156");
				paramMap.put("DEPTH", "2");
				paramMap.put("LEAF_YN", "Y");
				//affiliateLinkNoList.add(paramMap);
				affiliateLinkNoList.set(0, paramMap);
			}
		}
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

		searchVO.setAffiliateLinkNo(searchVO.getAffiliateLinkNo().replace("[", "").replace("]", ""));

        model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0017";

	}


	/**
	 * Desc : 위메프 조회 메소드
	 * @Method Name : selectWeMakePriceEdmSummaryList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/statistics/selectWeMakePriceEdmSummaryList.do")
	public String selectWeMakePriceEdmSummaryList(@ModelAttribute("searchVO") PSCMSTA0001SearchVO searchVO, HttpServletRequest request,ModelMap model) throws Exception {

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
		if(affiliateLinkNoList.size() > 0){
			ArrayList<String> knowshopping =  new ArrayList<String>();
			for(int a = 0 ; affiliateLinkNoList.size() > a ; a++){
				DataMap affiliateLink = affiliateLinkNoList.get(a);
				String AFFILIATE_LINK_NM = affiliateLink.getString("AFFILIATE_LINK_NM");
				String AFFILIATE_LINK_NO = affiliateLink.getString("AFFILIATE_LINK_NO");
				String UP_AFFILIATE_LINK_NO = affiliateLink.getString("UP_AFFILIATE_LINK_NO");
				String DEPTH = affiliateLink.getString("DEPTH");
				String LEAF_YN = affiliateLink.getString("LEAF_YN");
				if(!AFFILIATE_LINK_NO.equals("0156")){
					knowshopping.add(AFFILIATE_LINK_NO);
				}
			}
			if(knowshopping.size() > 0){
				DataMap paramMap = new DataMap();
				paramMap.put("AFFILIATE_LINK_NM", "원더쇼핑 합계");
				paramMap.put("AFFILIATE_LINK_NO", knowshopping);
				paramMap.put("UP_AFFILIATE_LINK_NO", "0156");
				paramMap.put("DEPTH", "2");
				paramMap.put("LEAF_YN", "Y");
				//affiliateLinkNoList.add(paramMap);
				affiliateLinkNoList.set(0, paramMap);
			}
		}
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
		searchVO.setAffiliateLinkNo(SecureUtil.sqlValid(searchVO.getAffiliateLinkNo().replace("[", "").replace("]", "")));
		String [] ary = {"deLete","asd", "JOIN"};
		ary = SecureUtil.sqlValidArray(ary);
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

		return "statistics/PSCMSTA0017";
	}



	/**
	 * Desc : 위메프 엑셀조회 메소드
	 * @Method Name : selectWeMakePriceEdmSummaryListExcel
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/statistics/selectWeMakePriceEdmSummaryListExcel.do")
	public String selectWeMakePriceEdmSummaryListExcel(@ModelAttribute("searchVO") PSCMSTA0001SearchVO searchVO, ModelMap model) throws Exception {

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

		searchVO.setAffiliateLinkNo(SecureUtil.sqlValid(searchVO.getAffiliateLinkNo().replace("[", "").replace("]", "")));

		// 데이터 조회
		List<DataMap> list = pscmsta0001Service.selectNaverEdmSummaryListExcel(searchVO);

		model.addAttribute("list", list);
		return "statistics/PSCMSTA001701";
	}
}
