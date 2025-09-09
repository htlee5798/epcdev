package com.lottemart.epc.order.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.order.model.PSCMORD0009VO;
import com.lottemart.epc.order.service.PSCMORD0009Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMORD0009Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMORD0009Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMORD0009Service pscmord0009Service;

	//교환/반품/취소 목록
	@RequestMapping(value = "order/viewPartnerReturnList.do")
	public String viewPartnerReturnList(@ModelAttribute("searchVO") PSCMORD0009VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		String from_date = "";     // 기간(시작)
		String to_date 	 = "";     // 기간(끝)

		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, 0);
		NowDate2.add(Calendar.DATE, -7);

		String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");

		// 초최 오픈시 Default 값 세팅
		if(from_date == null || from_date.equals("")) from_date = today_date2;
		if(to_date   == null || to_date.equals(""))   to_date   = today_date;

		searchVO.setStartDate(from_date);
		searchVO.setEndDate(to_date);
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		searchVO.setMajorCd("OR001");
		List<DataMap> OR001List = pscmord0009Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("OR002");
		List<DataMap> OR002List = pscmord0009Service.getTetCodeList(searchVO);

		model.addAttribute("OR001List", OR001List); //주문/취소/반품구분 코드
		model.addAttribute("OR002List", OR002List); //주문상태 코드

        model.addAttribute("searchVO", searchVO);
		return "order/PSCMORD0009";
	}

	//교환/반품/취소 목록 조회
//	@RequestMapping(value = "order/selectPartnerReturnList.do")
//	public String selectPartnerReturnList(@ModelAttribute("searchVO") PSCMORD0009VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
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
//			String rowsPerPage = StringUtil.null2string(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page"));
//			DataMap paramMap = new DataMap();
//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage", rowsPerPage);
//			paramMap.put("startDate", gdReq.getParam("startDate"));
//			paramMap.put("endDate", gdReq.getParam("endDate"));
//			paramMap.put("ordRtnDivnCd", gdReq.getParam("ordRtnDivnCd"));
//			paramMap.put("ordStsCd", gdReq.getParam("ordStsCd"));
//			paramMap.put("searchType",  gdReq.getParam("searchType"));
//			paramMap.put("searchContent",  gdReq.getParam("searchContent"));
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
//			if("06".equals(epcLoginVO.getVendorTypeCd())){
//
//				if(((ArrayList) paramMap.get("vendorId")).get(0).toString().indexOf("T")  < 0 ){
//					paramMap.put("vendorMode","V");
//				}
//			}
//
//			List<DataMap> list = pscmord0009Service.selectPartnerReturnList(paramMap);
//			// 조회된 데이터 가 없는 경우의 처리
//	        if(list == null || list.size() == 0) {
//	            //gdRes.setMessage("조회된 데이터가 없습니다.");
//	            gdRes.setStatus("false");
//	    		request.setAttribute("wizeGridResult", gdRes);
//	    		return "common/wiseGridResult";
//	        }
//
//	    	// 조회된 데이터 가 없는 경우의 처리
//	        if(list == null || list.size() == 0) {
//	            //gdRes.setMessage("조회된 데이터가 없습니다.");
//	            gdRes.setStatus("false");
//	    		request.setAttribute("wizeGridResult", gdRes);
//	    		return "common/wiseGridResult";
//	        }
//
//	        // GridData 셋팅
//	        for(int i = 0; i < list.size(); i++) {
//	        	DataMap map = list.get(i);
//
//	    		gdRes.getHeader("ORD_DY"  ).addValue(map.getString("ORD_DY"),"");
//	    		gdRes.getHeader("ORD_STS_NM").addValue(map.getString("ORD_STS_NM"),"");
//	    		gdRes.getHeader("FIRST_ORDER_ID").addValue(map.getString("FIRST_ORDER_ID"), "");
//	        	gdRes.getHeader("ORDER_ID").addValue(map.getString("ORDER_ID"), "");
//	        	gdRes.getHeader("PROD_CD").addValue(map.getString("PROD_CD"),"");
//	        	gdRes.getHeader("PROD_NM").addValue(map.getString("PROD_NM"),"");
//	        	gdRes.getHeader("ORD_QTY").addValue(map.getString("ORD_QTY"),"");
//	        	gdRes.getHeader("ACCEPT_QTY").addValue(map.getString("ACCEPT_QTY"),"");
//	        	gdRes.getHeader("CURR_SELL_PRC"  ).addValue(map.getString("CURR_SELL_PRC"),"");
//	        	gdRes.getHeader("ORD_AMT").addValue(map.getString("ORD_AMT"),"");
//	        	gdRes.getHeader("TOT_SELL_AMT"  ).addValue(map.getString("TOT_SELL_AMT"),"");
//	        	gdRes.getHeader("ORD_CANCEL_REASON_CD"  ).addValue(map.getString("ORD_CANCEL_REASON_CD"),"");
//	        	gdRes.getHeader("ORD_CANCEL_REASON_NM"  ).addValue(map.getString("ORD_CANCEL_REASON_NM"),"");
//	        	gdRes.getHeader("MEMO"  ).addValue(map.getString("MEMO"),"");
//	        	gdRes.getHeader("ORD_RTN_DIVN_CD"  ).addValue(map.getString("ORD_RTN_DIVN_CD"),"");
//	        }
//
//	        String totalCount = list.get(0).getString("TOTAL_COUNT");
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
//		return "common/wiseGridResult";
//	}



	@RequestMapping(value = "order/selectPartnerReturnList.do")
	public @ResponseBody Map selectPartnerReturnList(@ModelAttribute("searchVO") PSCMORD0009VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		Map<String, Object> rtnMap = new HashMap();

		try {

			DataMap paramMap = new DataMap(request);

			String rowPerPage  = paramMap.getString("rowsPerPage");
			String currentPage = paramMap.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow", Integer.toString(endRow));
			paramMap.put("startDate", paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDate", paramMap.getString("endDate").replaceAll("-", ""));
			paramMap.put("ordRtnDivnCd", paramMap.getString("ordRtnDivnCd"));
			paramMap.put("ordStsCd", paramMap.getString("ordStsCd"));
			paramMap.put("searchType",  paramMap.getString("searchType"));
			paramMap.put("searchContent",  paramMap.getString("searchContent"));

			// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if("".equals(paramMap.getString("vendorId"))) {
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(paramMap.getString("vendorId"));
				paramMap.put("vendorId", vendorList);

			}

			if("06".equals(epcLoginVO.getVendorTypeCd())){

				if(((ArrayList) paramMap.get("vendorId")).get(0).toString().indexOf("T")  < 0 ){
					paramMap.put("vendorMode","V");
				}
			}

			List<DataMap> list = pscmord0009Service.selectPartnerReturnList(paramMap);

			String totalCount = "0";

			if(list.size() > 0){
				totalCount = list.get(0).getString("TOTAL_COUNT");
			}


			rtnMap = JsonUtils.convertList2Json((List)list, Integer.parseInt(totalCount), currentPage);

			// 처리성공
	        rtnMap.put("result", true);

		} catch(Exception e) {
			logger.error("error --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping(value = "order/selectPartnerReturnListExcel.do")
	public void selectPartnerReturnListExcel(@ModelAttribute("searchVO") PSCMORD0009VO searchVO, HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {


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

		DataMap paramMap = new DataMap();

		paramMap.put("startDate", searchVO.getStartDate().replaceAll("-", ""));
		paramMap.put("endDate", searchVO.getEndDate().replaceAll("-", ""));
		paramMap.put("ordRtnDivnCd", searchVO.getOrdRtnDivnCd());
		paramMap.put("ordStsCd", searchVO.getOrdStsCd());
		paramMap.put("searchType",  searchVO.getSearchType());
		paramMap.put("searchContent",  searchVO.getSearchContent());
		paramMap.put("currentPage", "1");
		paramMap.put("rowsPerPage", "65000");

		// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
		if("".equals(searchVO.getVendorId())) {
			paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add(searchVO.getVendorId());
			paramMap.put("vendorId", vendorList);
		}

		if("06".equals(epcLoginVO.getVendorTypeCd())){

			if(((ArrayList) paramMap.get("vendorId")).get(0).toString().indexOf("T")  < 0 ){
				paramMap.put("vendorMode","V");
			}
		}

		List<DataMap> list = pscmord0009Service.selectPartnerReturnList(paramMap);

		model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        JsonUtils.IbsExcelDownload((List)list, request, response);

//		return "order/PSCMORD000901";
	}

}