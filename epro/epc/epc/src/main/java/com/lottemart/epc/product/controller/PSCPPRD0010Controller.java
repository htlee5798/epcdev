/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.product.model.PSCPPRD0010VO;
import com.lottemart.epc.product.service.PSCPPRD0010Service;

/**
 * @Class Name : PSCPPRD0010Controller
 * @Description : 상품검색 조회 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:08:56 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCPPRD0010Controller {
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0010Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCPPRD0010Service pscpprd0010Service;

	/**
	 * Desc : 상품검색 화면 이동 메소드
	 * @Method Name : selectProductPopupView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectProductPopupView.do")
	public String selectProductPopupView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		List<DataMap> prodDivnCdList = pscpprd0010Service.selectProdDivnCdList();
		request.setAttribute("prodDivnCdList", prodDivnCdList);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addMonth(endDate, -1), "-");
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCPPRD0010";
	}

	@RequestMapping(value = "/product/selectProductExcelPopupView.do")
	public String selectProductExcelPopupView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "product/PSCPPRD001002";
	}

	/**
	 * Desc : 상품검색 조회 메소드
	 * @Method Name : selectProductPopupList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "/product/selectProductPopupList.do")
//	public String selectProductPopupList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//
//		request.setAttribute("epcLoginVO", epcLoginVO);
//
//		GridData gdRes = new GridData();
//
//		try {
//			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData  gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//
//			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			DataMap paramMap = new DataMap();
//			int currentPage = Integer.parseInt(gdReq.getParam("currentPage"));
//			int rowsPerPage = Integer.parseInt(StringUtil.null2str(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page")));
//			paramMap.put("currentPage", currentPage);
//			paramMap.put("rowsPerPage", rowsPerPage);
//			paramMap.put("startRecord", (currentPage-1) * rowsPerPage + 1);
//			paramMap.put("endRecord", currentPage * rowsPerPage);
//
//
//			// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
//			if("".equals(gdReq.getParam("vendorId"))) {
//				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
//			} else {
//				ArrayList<String> vendorList = new ArrayList<String>();
//				vendorList.add(gdReq.getParam("vendorId"));
//				paramMap.put("vendorId", vendorList);
//
//			}
//			paramMap.put("cmbnMallSellPsbtYn", gdReq.getParam("cmbnMallSellPsbtYn"));
//			paramMap.put("dispYn", gdReq.getParam("dispYn"));
//			paramMap.put("searchCondition", gdReq.getParam("searchCondition"));
//			paramMap.put("searchWord", gdReq.getParam("searchWord"));
//			paramMap.put("onlineProdYn", "0".equals(gdReq.getParam("onlineProdYn"))?"N":"Y");
//			paramMap.put("searchDateYn", "0".equals(gdReq.getParam("searchDateYn"))?"N":"Y");
//			paramMap.put("searchDateType", gdReq.getParam("searchDateType"));
//			paramMap.put("startDate", gdReq.getParam("startDate"));
//			paramMap.put("endDate", gdReq.getParam("endDate"));
//			paramMap.put("categoryId", gdReq.getParam("categoryId"));
//
//			// 데이터 조회
//			List<DataMap> pscpprd0010List = pscpprd0010Service.selectProductPopupList(paramMap);
//
//			int size = pscpprd0010List.size();
//
//			// 조회된 데이터 가 없는 경우의 처리
//	        if(pscpprd0010List == null || size == 0) {
//	            gdRes.setStatus("false");
//	    		request.setAttribute("wizeGridResult", gdRes);
//	    		return "common/wiseGridResult";
//	        }
//
//	        // GridData 셋팅
//	        for(int i = 0; i < size; i++) {
//	        	DataMap map = pscpprd0010List.get(i);
//
//	        	gdRes.getHeader("crud").addValue("", "");
//	        	gdRes.getHeader("selected").addValue("0", "");
//	        	gdRes.getHeader("prodCd").addValue(map.getString("PROD_CD"),"");
//	        	gdRes.getHeader("mdProdCd").addValue(map.getString("MD_PROD_CD"),"");
//	        	gdRes.getHeader("mdSrcmkCd").addValue(map.getString("MD_SRCMK_CD"),"");
//	        	gdRes.getHeader("prodNm").addValue(map.getString("PROD_NM"),"");
//	        	gdRes.getHeader("categoryNm").addValue(map.getString("CATEGORY_NM"),"");
//	        	gdRes.getHeader("dispYn").addValue(map.getString("DISP_YN"),"");
//	        	gdRes.getHeader("absenceYn").addValue(map.getString("ABSENCE_YN"),"");
//	        	gdRes.getHeader("cullSellPrc").addValue("","");
//	        }
//
//			List<DataMap> pscpprd0010Count = pscpprd0010Service.selectProductPopupCount(paramMap);
//	        String totalCount = pscpprd0010Count.get(0).getString("TOTAL_COUNT");
//
//	        // 페이징 변수
//	        gdRes.addParam("totalCount", totalCount);
//	        gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//	        gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//	        gdRes.setStatus("true");
//
//		} catch(Exception e) {
//			gdRes.setStatus("false");
//			gdRes.setMessage(e.getMessage());
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//
//		return "common/wiseGridResult";
//	}


	/**
	 * Desc : 상품검색 조회 메소드
	 * @Method Name : selectProductPopupList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectProductPopupList.do")
	public @ResponseBody Map selectProductPopupList(HttpServletRequest request, HttpServletResponse response) throws Exception {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData  gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));

			String rowPerPage  = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);
			param.put("startRecord", (currPage-1) * rowPage + 1);
			param.put("endRecord", currPage * rowPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));


			// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if("".equals(param.getString("vendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("vendorId"));
				param.put("vendorId", vendorList);

			}
			param.put("onlineProdYn", "0".equals(param.getString("onlineProdYn"))?"N":"Y");
			param.put("searchDateYn", "0".equals(param.getString("searchDateYn"))?"N":"Y");

			// 데이터 조회
			List<PSCPPRD0010VO> pscpprd0010List = pscpprd0010Service.selectProductPopupList(param);

			int size = pscpprd0010List.size();

			List<DataMap> pscpprd0010Count = pscpprd0010Service.selectProductPopupCount(param);
			int totalCount = Integer.valueOf(pscpprd0010Count.get(0).getString("TOTAL_COUNT"));

			rtnMap = JsonUtils.convertList2Json((List)pscpprd0010List, totalCount, currentPage);

			// 처리성공
	        rtnMap.put("result", true);


		} catch(Exception e) {
		        rtnMap.put("result", false);
		        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

//	@RequestMapping(value = "/product/selectProductArrayPopupList.do")
//	public String selectProductArrayPopupList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//
//		request.setAttribute("epcLoginVO", epcLoginVO);
//
//		GridData gdRes = new GridData();
//
//		try {
//			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData  gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//
//			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			DataMap paramMap = new DataMap();
//			int currentPage = Integer.parseInt(gdReq.getParam("currentPage"));
//			int rowsPerPage = Integer.parseInt(StringUtil.null2str(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page")));
//			paramMap.put("currentPage", currentPage);
//			paramMap.put("rowsPerPage", rowsPerPage);
//			paramMap.put("startRecord", (currentPage-1) * rowsPerPage + 1);
//			paramMap.put("endRecord", currentPage * rowsPerPage);
//
//
//			// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
//			if("".equals(gdReq.getParam("vendorId"))) {
//				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
//			} else {
//				ArrayList<String> vendorList = new ArrayList<String>();
//				vendorList.add(gdReq.getParam("vendorId"));
//				paramMap.put("vendorId", vendorList);
//
//			}
//
//			String[] prodArrays = gdReq.getParam("prodArray").split(",");
//			if(prodArrays.length > 0){
//				ArrayList<String> prodCdList = new ArrayList<String>();
//				for(int i=0;i<prodArrays.length;i++){
//					String[] prodCdArrays = prodArrays[i].split(":");
//					prodCdList.add(prodCdArrays[0]);
//					paramMap.put("prodCdList", prodCdList);
//				}
//			}
//
//			//request.setAttribute("prodArray", gdReq.getParam("prodArray"));
//
//			// 데이터 조회
//			List<DataMap> pscpprd0010List = pscpprd0010Service.selectProductArrayPopupList(paramMap);
//
//			int size = pscpprd0010List.size();
//
//			// 조회된 데이터 가 없는 경우의 처리
//	        if(pscpprd0010List == null || size == 0) {
//	            gdRes.setStatus("false");
//	    		request.setAttribute("wizeGridResult", gdRes);
//	    		return "common/wiseGridResult";
//	        }
//
//	        // GridData 셋팅
//	        for(int i = 0; i < size; i++) {
//	        	DataMap map = pscpprd0010List.get(i);
//
//	        	gdRes.getHeader("crud").addValue("", "");
//	        	gdRes.getHeader("selected").addValue("0", "");
//	        	gdRes.getHeader("prodCd").addValue(map.getString("PROD_CD"),"");
//	        	gdRes.getHeader("mdProdCd").addValue(map.getString("MD_PROD_CD"),"");
//	        	gdRes.getHeader("mdSrcmkCd").addValue(map.getString("MD_SRCMK_CD"),"");
//	        	gdRes.getHeader("prodNm").addValue(map.getString("PROD_NM"),"");
//	        	gdRes.getHeader("categoryNm").addValue(map.getString("CATEGORY_NM"),"");
//	        	gdRes.getHeader("dispYn").addValue(map.getString("DISP_YN"),"");
//	        	gdRes.getHeader("absenceYn").addValue(map.getString("ABSENCE_YN"),"");
//	        	String sCullSellPrc = "";
//				if(prodArrays.length > 0){
//					for(int k=0;k<prodArrays.length;k++){
//						String[] prodCdArrays = prodArrays[k].split(":");
//						if (map.getString("PROD_CD").equals(prodCdArrays[0])){
//
//							if (prodCdArrays.length > 1){
//								sCullSellPrc = prodCdArrays[1];
//							}
//						}
//					}
//				}
//
//				gdRes.getHeader("cullSellPrc").addValue(sCullSellPrc,"");
//
//	        }
//
//	        String totalCount = size+"";
//
//	        // 페이징 변수
//	        gdRes.addParam("totalCount", totalCount);
//	        gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//	        gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//	        gdRes.setStatus("true");
//
//		} catch(Exception e) {
//			gdRes.setStatus("false");
//			gdRes.setMessage(e.getMessage());
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//
//		return "common/wiseGridResult";
//	}


	@RequestMapping(value = "/product/selectProductArrayPopupList.do")
	public @ResponseBody Map selectProductArrayPopupList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("================================================================================");
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		Map rtnMap = new HashMap<String, Object>();

		try {
			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData  gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));

			DataMap param = new DataMap(request);

//			DataMap paramMap = new DataMap();
			String rowPerPage  = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);
			param.put("startRecord", (currPage-1) * rowPage + 1);
			param.put("endRecord", currPage * rowPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDt", param.getString("startDt").replaceAll("-", ""));
			param.put("endDt", param.getString("endDt").replaceAll("-", ""));

			// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if("".equals(param.getString("vendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("vendorId"));
				param.put("vendorId", vendorList);

			}

			String[] prodArrays = param.getString("selectedProdCd").split(",");
//			String[] prodArrays = gdReq.getParam("prodArray").split(",");
//			if(prodArrays.length > 0){
//				ArrayList<String> prodCdList = new ArrayList<String>();
//				for(int i=0;i<prodArrays.length;i++){
//					String[] prodCdArrays = prodArrays[i].split(":");
//					prodCdList.add(prodCdArrays[0]);
//					paramMap.put("prodCdList", prodCdList);
//				}
//			}
			ArrayList<String> prodCdList = new ArrayList<String>();
			for( String prodcd : prodArrays ){
				prodCdList.add((prodcd.replace("[", "")).replace("]", "").trim());
			}

			param.put("prodCdList", prodCdList);

//			logger.debug("prodCdList    =    "+prodCdList);

			//request.setAttribute("prodArray", gdReq.getParam("prodArray"));

			// 데이터 조회
			List<PSCPPRD0010VO> pscpprd0010List = pscpprd0010Service.selectProductArrayPopupList(param);

			int size = pscpprd0010List.size();

			String totalCount = size+"";

			rtnMap = JsonUtils.convertList2Json((List)pscpprd0010List, size, currentPage);

			// 처리성공
	        rtnMap.put("result", true);

		} catch(Exception e) {
//			logger .error("error --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 중분류 카테고리 조회 메소드
	 * @Method Name : selectMiddleCategoryList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectMiddleCategoryList.do")
	public String selectMiddleCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap paramMap = new DataMap();
		paramMap.put("category", request.getParameter("category"));
		List<DataMap> categoryList = pscpprd0010Service.selectMiddleCategoryList(paramMap);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("categoryName", "middle");
		request.setAttribute("parentFormName", SecureUtil.stripXSS(request.getParameter("parentFormName")));

		return "product/PSCPPRD001001";
	}

	/**
	 * Desc : 소분류 카테고리 조회 메소드
	 * @Method Name : selectSubCategoryList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectSubCategoryList.do")
	public String selectSubCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap paramMap = new DataMap();
		paramMap.put("category", request.getParameter("category"));
		paramMap.put("middleCategory", request.getParameter("middleCategory"));
		List<DataMap> categoryList = pscpprd0010Service.selectSubCategoryList(paramMap);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("categoryName", "sub");
		request.setAttribute("parentFormName", SecureUtil.stripXSS(request.getParameter("parentFormName")));

		return "product/PSCPPRD001001";
	}

	/**
	 * Desc : 세분류 카테고리 조회 메소드
	 * @Method Name : selectDetailCategoryList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectDetailCategoryList.do")
	public String selectDetailCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap paramMap = new DataMap();
		paramMap.put("category", request.getParameter("category"));
		paramMap.put("middleCategory", request.getParameter("middleCategory"));
		paramMap.put("subCategory", request.getParameter("subCategory"));
		// 2015.10.30 by kmlee 카테고리 체계 변경으로 사용하지 않는 함수임.
		// List<DataMap> categoryList = pscpprd0010Service.selectDetailCategoryList(paramMap);
		List<DataMap> categoryList = null;
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("categoryName", "detail");
		request.setAttribute("parentFormName", SecureUtil.stripXSS(request.getParameter("parentFormName")));

		return "product/PSCPPRD001001";
	}
}
