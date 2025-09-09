/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.product.model.PSCMPRD0009VO;
import com.lottemart.epc.product.service.PSCMPRD0009Service;

/**
 * @Class Name : PSCMPRD0009Controller
 * @Description : 상품가격변경요청리스트를 조회하는 Controller 클래스
 * @Modification Information
 *
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:22:13 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMPRD0009Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0009Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMPRD0009Service pscmprd0009Service;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonService commonService;

	/**
	 * Desc : 상품가격변경요청리스트 화면 이동하는 메소드
	 *
	 * @Method Name : selectPriceChangeView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectPriceChangeView.do")
	public String selectPriceChangeView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);
		request.setAttribute("rowsPerPage", "100");

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addMonth(endDate, -1), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0009";
	}

	/**
	 * Desc : 상품가격변경요청리스트 조회하는 메소드
	 *
	 * @Method Name : selectPriceChangeList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "/product/selectPriceChangeList.do")
//	public String selectPriceChangeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
//
//		GridData gdRes = new GridData();
//
//		try {
//			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//
//			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			config.getString("count.row.per.page");
//
//			DataMap paramMap = new DataMap();
//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage",StringUtil.null2str(gdReq.getParam("rowsPerPage"),config.getString("count.row.per.page")));
//
//			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
//			if ("".equals(gdReq.getParam("vendorId"))) {
//				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
//			} else {
//				ArrayList<String> vendorList = new ArrayList<String>();
//				vendorList.add(gdReq.getParam("vendorId"));
//				paramMap.put("vendorId", vendorList);
//			}
//			paramMap.put("startDate", gdReq.getParam("startDate"));
//			paramMap.put("endDate", gdReq.getParam("endDate"));
//			paramMap.put("aprvYn", gdReq.getParam("aprvYn"));
//			paramMap.put("searchCondition", gdReq.getParam("searchCondition"));
//			paramMap.put("searchWord", gdReq.getParam("searchWord"));
//
//			// 데이터 조회
//			List<DataMap> pscmprd0009List = pscmprd0009Service.selectPriceChangeList(paramMap);
//
//			int size = pscmprd0009List.size();
//
//			// 조회된 데이터 가 없는 경우의 처리
//			if (pscmprd0009List == null || size == 0) {
//				gdRes.setStatus("false");
//				request.setAttribute("wizeGridResult", gdRes);
//				return "common/wiseGridResult";
//			}
//
//			// GridData 셋팅
//			for (int i = 0; i < size; i++) {
//				DataMap map = pscmprd0009List.get(i);
//
//				gdRes.getHeader("crud").addValue("", "");
//				gdRes.getHeader("isUpd").addValue("U", "");
//				gdRes.getHeader("selected").addValue("0", "");
//				gdRes.getHeader("prodCd").addValue(map.getString("PROD_CD"), "");
//				gdRes.getHeader("prodNm").addValue(map.getString("PROD_NM"), "");
//				gdRes.getHeader("strCd").addValue(map.getString("STR_CD"), "");
//				gdRes.getHeader("strNm").addValue(map.getString("STR_NM"), "");
//				gdRes.getHeader("itemCd").addValue(map.getString("ITEM_CD"), "");
//				gdRes.getHeader("reqSeq").addValue(map.getString("REQ_SEQ"), "");
//				gdRes.getHeader("chgbBuyPrc").addValue(map.getString("CHGB_BUY_PRC"), "");
//				gdRes.getHeader("chgbSellPrc").addValue(map.getString("CHGB_SELL_PRC"), "");
//				gdRes.getHeader("chgbCurrSellPrc").addValue(map.getString("CHGB_CURR_SELL_PRC"), "");
//				gdRes.getHeader("chgaBuyPrc").addValue(map.getString("CHGA_BUY_PRC"), "");
//				gdRes.getHeader("chgaCurrSellPrc").addValue(map.getString("CHGA_CURR_SELL_PRC"), "");
//				gdRes.getHeader("profitRateS").addValue(map.getString("PROFIT_RATE"), "");
//				gdRes.getHeader("profitRate").addValue(map.getString("PROFIT_RATE"), "");
//				gdRes.getHeader("taxatDivnCd").addValue(map.getString("TAXAT_DIVN_CD"), "");
//				gdRes.getHeader("reqDate").addValue(map.getString("REQ_DATE"),"");
//				gdRes.getHeader("aprvDate").addValue(map.getString("APRV_DATE"), "");
//				gdRes.getHeader("aprvYn").addValue(map.getString("APRV_YN"), "");
//				gdRes.getHeader("vendorId").addValue(map.getString("VENDOR_ID"), "");
//				gdRes.getHeader("chgReqReasonContent").addValue(map.getString("CHG_REQ_REASON_CONTENT"), "");
//			}
//
//			String totalCount = pscmprd0009List.get(0).getString("TOTAL_COUNT");
//
//			// 페이징 변수
//			gdRes.addParam("totalCount", totalCount);
//			gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//			gdRes.setStatus("true");
//
//		} catch (Exception e) {
//			gdRes.setStatus("false");
//			gdRes.setMessage(e.getMessage());
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//
//		return "common/wiseGridResult";
//	}

	/**
	 * Desc : 상품가격변경요청리스트 조회하는 메소드
	 *
	 * @Method Name : selectPriceChangeList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectPriceChangeList.do")
	public @ResponseBody Map selectPriceChangeList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		try {
			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			config.getString("count.row.per.page");

			DataMap param = new DataMap(request);

			String rowPerPage  = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if ("".equals(param.getString("vendorId")) || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) ) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("vendorId"));
				param.put("vendorId", vendorList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
				}
			}
			
			// 데이터 조회
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date to = format.parse(param.getString("endDate").replaceAll("-", ""));
			Date from = format.parse(param.getString("startDate").replaceAll("-", ""));
			long diffDay = (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000);

			if (diffDay >= 30) {
				param.put("planOneMonth", "N");
			} else {
				param.put("planOneMonth", "Y");
			}

			// 데이터 조회
			List<PSCMPRD0009VO> pscmprd0009List = pscmprd0009Service.selectPriceChangeList(param);

			int size = pscmprd0009List.size();
			int totalCount = 0;
			if(size > 0 ){
				totalCount = Integer.valueOf(pscmprd0009List.get(0).getTotalCount());
			}

			rtnMap = JsonUtils.convertList2Json((List) pscmprd0009List, totalCount, currentPage);

			// 처리성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드
	 *
	 * @Method Name : insertPriceChange
	 * @param request
	 * @param response
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "/product/insertPriceChange.do")
//	public void insertPriceChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
//
//		GridData gdRes = new GridData();
//
//		// Encode Type을 UTF-8로 변환한다.
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//
//		try {
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//
//			// 모드셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			// 처리수행
//			int rowCount = gdReq.getHeader("selected").getRowCount();
//
//			List<PSCMPRD0009VO> pscmprd0009VOList = new ArrayList<PSCMPRD0009VO>();
//			PSCMPRD0009VO pscmprd0009VO = null;
//
//			// header data VO객체에 셋팅
//			for (int index = 0; index < rowCount; index++) {
//				pscmprd0009VO = new PSCMPRD0009VO();
//				pscmprd0009VO = (PSCMPRD0009VO) WiseGridUtil.getWiseGridHeaderDataToObject(index, gdReq,pscmprd0009VO);
//
//				pscmprd0009VOList.add(pscmprd0009VO);
//			}
//
//			// 등록 수행
//			gdRes = doSave(gdReq, pscmprd0009VOList);
//
//		} catch (Exception e) {
//			gdRes.setMessage(e.getMessage());
//		} finally {
//			try {
//				// 자료구조를 전문으로 변경해 Write한다.
//				OperateGridData.write(gdRes, out);
//			} catch (Exception e) {
//				logger.error(e + "");
//			}
//		}
//	}

	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드
	 *
	 * @Method Name : insertPriceChange
	 * @param request
	 * @param response
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/insertPriceChange.do")
	public @ResponseBody JSONObject  insertPriceChange(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		JSONObject jObj = new JSONObject();

		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();

		try {
			DataMap param = new DataMap(request);

			// 모드셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 처리수행
//			int rowCount = gdReq.getHeader("selected").getRowCount();

			String[] rowCount = request.getParameterValues("selected");

			String[] status = request.getParameterValues("s_status");
			String[] crud = request.getParameterValues("crud");
			String[] prodCd = request.getParameterValues("prodCd");
			String[] itemCd = request.getParameterValues("itemCd");
			String[] strCd = request.getParameterValues("strCd");
			String[] chgbBuyPrc = request.getParameterValues("chgbBuyPrc");
			String[] chgbSellPrc = request.getParameterValues("chgbSellPrc");
			String[] chgbCurrSellPrc = request.getParameterValues("chgbCurrSellPrc");
			String[] taxatDivnCd = request.getParameterValues("taxatDivnCd");
			String[] profitRateS = request.getParameterValues("profitRateS");
			String[] chgaCurrSellPrc = request.getParameterValues("chgaCurrSellPrc");
			String[] aprvYn = request.getParameterValues("aprvYn");
//			String[] aprvDate = request.getParameterValues("aprvDate");
			String[] chgReqReasonContent = request.getParameterValues("chgReqReasonContent");
			String[] vendorId = request.getParameterValues("vendorId");
			String[] reqSeq = request.getParameterValues("reqSeq");
			String[] selected = request.getParameterValues("selected");

			List<PSCMPRD0009VO> pscmprd0009VOList = new ArrayList<PSCMPRD0009VO>();
			PSCMPRD0009VO pscmprd0009VO = new PSCMPRD0009VO();

			// header data VO객체에 셋팅
			for (int i = 0; i< rowCount.length; i++) {
				int resultCnt = 0;
			if( "1".equals(selected[i])){
					pscmprd0009VO.setProdCd(prodCd[i]);
					pscmprd0009VO.setItemCd(itemCd[i]);
					pscmprd0009VO.setStrCd(strCd[i]);
					pscmprd0009VO.setChgbBuyPrc(Long.parseLong(chgbBuyPrc[i]));
					pscmprd0009VO.setChgbSellPrc(Long.parseLong(chgbSellPrc[i]));
					pscmprd0009VO.setChgbCurrSellPrc(Long.parseLong(chgbCurrSellPrc[i]));
					pscmprd0009VO.setTaxatDivnCd(taxatDivnCd[i]);
					pscmprd0009VO.setProfitRateS(profitRateS[i]);
					pscmprd0009VO.setChgaCurrSellPrc(Long.parseLong(chgaCurrSellPrc[i]));
					pscmprd0009VO.setAprvYn("N");
					pscmprd0009VO.setVendorId(vendorId[i]);
					pscmprd0009VO.setReqPsn(vendorId[i]);
					pscmprd0009VO.setRegId(vendorId[i]);
					pscmprd0009VO.setModId(vendorId[i]);
	//				pscmprd0009VO.setAprvDate(aprvDate[i]);
					pscmprd0009VO.setChgReqReasonContent(chgReqReasonContent[i]);
					pscmprd0009VO.setReqSeq(reqSeq[i]);

	//				logger.debug( "★★★★★★★★★★★★★★★★★★★★★★★    =    "+vendorId[i] );
					if( "I".equals(crud[i]) ) {
						resultCnt = pscmprd0009Service.insertPriceChangeReq(pscmprd0009VO);
					}else if ( "U".equals(crud[i]) ) {
						resultCnt = pscmprd0009Service.updatePriceChangeReq(pscmprd0009VO);
					}else{
						resultCnt = pscmprd0009Service.insertPriceChangeReq(pscmprd0009VO);
					}

					//처리 결과 메세지 생성
					jObj.put("Code", 1);
					jObj.put("Message", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));

				}
			}
//			rtnMap.put("saveCnt", rowCount.length);
		}catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드 (서비스 클래스 호출)
	 *
	 * @Method Name : doSave
	 * @param gdReq
	 * @param pscmprd0009VO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public GridData doSave(GridData gdReq, List<PSCMPRD0009VO> pscmprd0009VO) throws Exception {

		GridData gdRes = new GridData();
		String returnData = "";
		String mode = gdReq.getParam("mode");

		try {
//			pscmprd0009Service.insertPriceChangeReq(pscmprd0009VO);
			/* 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다 */
			returnData = getSendData(pscmprd0009VO.size(), mode);
			gdRes.addParam("saveData", returnData);
			gdRes.setStatus("true");

		} catch (Exception e) {
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.error(e + "");
		}
		gdRes.addParam("mode", gdReq.getParam("mode"));
		return gdRes;
	}

	/**
	 * Desc : 결과에 따른 메시지 처리 메소드
	 *
	 * @Method Name : getSendData
	 * @param size
	 * @param mode
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	private String getSendData(int size, String mode) {
		StringBuffer sbData = new StringBuffer();
		if ("save".equals(mode)) {
			sbData.append(size);
			sbData.append(" 건의 데이터가 저장되었습니다.\n\n");
		} else if ("delete".equals(mode)) {
			sbData.append(size);
			sbData.append(" 건의 데이터가 삭제되었습니다.\n\n");
		}
		return sbData.toString();
	}

	/**
	 * Desc : 상품단품 정보를 조회하는 메소드 (현재 승인여부 'N'인 데이터는 제외)
	 *
	 * @Method Name : selectProductItemList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "/product/selectProductItemList.do")
//	public String selectProductItemList(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
//
//		GridData gdRes = new GridData();
//
//		try {
//			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//
//			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			DataMap paramMap = new DataMap();
//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage",StringUtil.null2str(gdReq.getParam("rowsPerPage"),config.getString("count.row.per.page")));
//			paramMap.put("strCd", config.getString("online.rep.str.cd"));
//			String[] selectedProdCd = gdReq.getParam("selectedProdCd").split(",");
//			ArrayList<String> list = new ArrayList<String>();
//			for (int i = 0; i < selectedProdCd.length; i++) {
//				if (!"".equals(selectedProdCd[i])) {
//					String[] prodCdArrays = selectedProdCd[i].split(":");
//					list.add(prodCdArrays[0]);
//				}
//			}
//
//			paramMap.put("selectedProdCd", list);
//
//			// 기 요청된 단품 경고 메시지 처리를 위한 조회
//			List<DataMap> pscmprd0009DupCount = pscmprd0009Service.selectProductItemDupCount(paramMap);
//
//			String dupCount = pscmprd0009DupCount.get(0).getString("TOTAL_COUNT");
//
//			// 데이터 조회
//			List<DataMap> pscmprd0009List = pscmprd0009Service.selectProductItemList(paramMap);
//
//			int size = pscmprd0009List.size();
//
//			// GridData 셋팅
//			for (int i = 0; i < size; i++) {
//				DataMap map = pscmprd0009List.get(i);
//
//				gdRes.getHeader("crud").addValue("", "");
//				gdRes.getHeader("isUpd").addValue("C", "");
//				gdRes.getHeader("selected").addValue("0", "");
//				gdRes.getHeader("prodCd").addValue(map.getString("PROD_CD"), "");
//				gdRes.getHeader("prodNm").addValue(map.getString("PROD_NM"), "");
//				gdRes.getHeader("strCd").addValue(map.getString("STR_CD"), "");
//				gdRes.getHeader("strNm").addValue(map.getString("STR_NM"), "");
//				gdRes.getHeader("itemCd").addValue(map.getString("ITEM_CD"), "");
//				gdRes.getHeader("reqSeq").addValue("", "");
//				gdRes.getHeader("chgbBuyPrc").addValue(map.getString("BUY_PRC"), "");
//				gdRes.getHeader("chgbSellPrc").addValue(map.getString("SELL_PRC"), "");
//				// 요청원가 수정 보류
//				// gdRes.getHeader("chgaBuyPrc").addValue("","");
//				gdRes.getHeader("chgaBuyPrc").addValue(map.getString("BUY_PRC"), "");
//				gdRes.getHeader("chgbCurrSellPrc").addValue(map.getString("CURR_SELL_PRC"), "");
//				gdRes.getHeader("profitRate").addValue(map.getString("PROFIT_RATE"), "");
//				gdRes.getHeader("profitRateS").addValue(map.getString("PROFIT_RATE"), "");
//				gdRes.getHeader("taxatDivnCd").addValue(map.getString("TAXAT_DIVN_CD"), "");
//
//				String sCullSellPrc = "";
//				for (int k = 0; k < selectedProdCd.length; k++) {
//					if (!"".equals(selectedProdCd[k])) {
//						String[] prodCdArrays = selectedProdCd[k].split(":");
//						if (map.getString("PROD_CD").equals(prodCdArrays[0])){
//							if (prodCdArrays.length > 1){
//								sCullSellPrc = prodCdArrays[1];
//							}
//						}
//					}
//				}
//
//				gdRes.getHeader("chgaCurrSellPrc").addValue(sCullSellPrc, "");
//				gdRes.getHeader("reqDate").addValue(map.getString("REQ_DATE"),"");
//				gdRes.getHeader("aprvDate").addValue(map.getString("APRV_DATE"), "");
//				gdRes.getHeader("aprvYn").addValue(map.getString("APRV_YN"), "");
//				gdRes.getHeader("vendorId").addValue(map.getString("VENDOR_ID"), "");
//				gdRes.getHeader("chgReqReasonContent").addValue("", "");
//			}
//
//			// 페이징 변수
//			gdRes.addParam("totalCount", size + "");
//			gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//			gdRes.setStatus("true");
//
//			gdRes.addParam("dupCount", dupCount);
//
//		} catch (Exception e) {
//			gdRes.setStatus("false");
//			gdRes.setMessage(e.getMessage());
//			logger.error(e + "");
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//
//		return "common/wiseGridResult";
//	}

	/**
	 * Desc : 상품단품 정보를 조회하는 메소드 (현재 승인여부 'N'인 데이터는 제외)
	 *
	 * @Method Name : selectProductItemList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectProductItemList.do")
	public @ResponseBody Map selectProductItemList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			DataMap param = new DataMap(request);

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
			int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDt", param.getString("startDt").replaceAll("-", ""));
			param.put("endDt", param.getString("endDt").replaceAll("-", ""));

//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage",StringUtil.null2str(gdReq.getParam("rowsPerPage"),config.getString("count.row.per.page")));
			param.put("strCd", config.getString("online.rep.str.cd"));

			String[] selectedProdCd = param.getString("selectedProdCd").split(",");
//			String[] selectedCullSellPrc = request.getParameterValues("selectedCullSellPrc"); //param.getString("selectedCullSellPrc");
			/*
			String[] selectedProdCd = param.getString("selectedProdCd").split(",");
			logger.debug("★★★★★★ =ininininininiiinininininininininin        =     "+selectedProdCd.length);
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < selectedProdCd.length; i++) {
				logger.debug("★★★★★★ = forforfor    =    "+selectedProdCd[i]);
				if (!"".equals(selectedProdCd[i])) {
					String[] prodCdArrays = selectedProdCd[i].split(":");
					logger.debug("★★★★★★★★★★★★★★★★★★★★         "+selectedProdCd[i]);
					list.add(selectedProdCd[i]);
				}
			}*/
			ArrayList<String> list = new ArrayList<String>();
			for( String prodcd : selectedProdCd ){
				list.add(prodcd.replace("[", "").replace("]", "").trim());
			}

			param.put("selectedProdCd", list);

			// 기 요청된 단품 경고 메시지 처리를 위한 조회
			List<DataMap> pscmprd0009DupCount = pscmprd0009Service.selectProductItemDupCount(param);

			String dupCount = pscmprd0009DupCount.get(0).getString("TOTAL_COUNT");

			// 데이터 조회
			List<PSCMPRD0009VO> pscmprd0009List = pscmprd0009Service.selectProductItemList(param);

			int size = 0;
//			 size =  pscmprd0009List.size();

			// 안씀
//			String sCullSellPrc = "";
//			for (int k = 0; k < selectedProdCd.length; k++) {
//				if (!"".equals(selectedProdCd[k])) {
//					String[] prodCdArrays = selectedProdCd[k].split(":");
//					if (map.getString("PROD_CD").equals(prodCdArrays[0])){
//						if (prodCdArrays.length > 1){
//							sCullSellPrc = prodCdArrays[1];
//						}
//					}
//				}
//			}
			rtnMap = JsonUtils.convertList2Json((List)pscmprd0009List, size, currentPage);

			// 처리성공
	        rtnMap.put("result", true);
	        rtnMap.put("dupCount", dupCount);
	        rtnMap.put("mode", "choice");
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 상품가격변경요청리스트 엑셀다운로드하는 메소드
	 *
	 * @Method Name : selectPriceChangeListExcel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectPriceChangeListExcel.do")
	public void selectPriceChangeListExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("searchVO") PSCMPRD0009VO searchVO, ModelMap model)
			throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()) || "".equals(searchVO.getVendorId())) {
			searchVO.setVendorList(LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendor = new ArrayList<String>();
			vendor.add(searchVO.getVendorId());
			searchVO.setVendorList(vendor);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId().replace("[", "").replace("]", "").trim())){
				searchVO.setVendorList(LoginUtil.getVendorList(epcLoginVO));
			}
		}

		// 데이터 조회
		List<PSCMPRD0009VO> list = pscmprd0009Service.selectPriceChangeListExcel(searchVO);
		model.addAttribute("list", list);
		JsonUtils.IbsExcelDownload((List)list, request, response);
//		return "product/PSCMPRD000901";
	}

}
