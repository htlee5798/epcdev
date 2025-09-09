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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.delivery.model.PSCMDLV0005VO;
import com.lottemart.epc.delivery.service.PSCMDLV0005Service;
import com.lottemart.epc.order.model.PSCMORD0001VO;
import com.lottemart.epc.order.service.PSCMORD0001Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMORD0001Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMORD0001Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMORD0001Service pscmord0001Service;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PSCMDLV0005Service pscmdlv0005Service;

	@RequestMapping(value = "order/viewOrderList.do")
	public String viewOrderList(@ModelAttribute("searchVO") PSCMORD0001VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

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
		NowDate2.add(Calendar.DATE, -21);

		String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");

		// 초최 오픈시 Default 값 세팅
		if(from_date == null || from_date.equals("")) from_date = today_date2;
		if(to_date   == null || to_date.equals(""))   to_date   = today_date;

		searchVO.setStartDate(from_date);
		searchVO.setEndDate(to_date);
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));
		searchVO.setStrCd(config.getString("online.rep.str.cd"));

		// 온라인 점포 리스트
		List<DataMap> strCdList = pscmord0001Service.selectAllOnlineStore();
		model.addAttribute("strCdList", strCdList);

		// 주문상태
		searchVO.setMajorCd("OR002");
		List<DataMap> ordStsCdList = pscmord0001Service.selectCodeList(searchVO);
		model.addAttribute("ordStsCdList", ordStsCdList);

		// 결제유형
		searchVO.setMajorCd("OR008");
		List<DataMap> setlTypeCdList = pscmord0001Service.selectCodeList(searchVO);
		model.addAttribute("setlTypeCdList", setlTypeCdList);

		// 매출상태
		searchVO.setMajorCd("OR003");
		List<DataMap> saleStsCdList = pscmord0001Service.selectCodeList(searchVO);
		model.addAttribute("saleStsCdList", saleStsCdList);

		// 발송, 미발송 alert용
		PSCMDLV0005VO  search0005VO =new PSCMDLV0005VO();
		search0005VO.setVendorId(epcLoginVO.getVendorId());
		List<DataMap> holyList = pscmdlv0005Service.selectPartnerFirmsStatus_All(search0005VO);
		request.setAttribute("AD_01", holyList.get(0).getString("AD_01"));	// 미확인건
		request.setAttribute("AD_02", holyList.get(0).getString("AD_02"));	// 발송 예정건
		request.setAttribute("T",'T');

        model.addAttribute("searchVO", searchVO);
		return "order/PSCMORD0001";
	}


//	@RequestMapping(value = "order/selectOrderList.do")
//	public String selectOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//
//		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
//		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
//		{
//
//		}
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
//			paramMap.put("orderId", gdReq.getParam("orderId"));
//			paramMap.put("custNm", gdReq.getParam("custNm"));
//			paramMap.put("ordStsCd",  gdReq.getParam("ordStsCd"));
//			paramMap.put("strCd",  gdReq.getParam("strCd"));
//			paramMap.put("setlTypeCd",  gdReq.getParam("setlTypeCd"));
//			paramMap.put("saleStsCd",  gdReq.getParam("saleStsCd"));
//			paramMap.put("searchType",  gdReq.getParam("searchType"));
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
//			String excelYn = StringUtil.null2str(gdReq.getParam("excelYn"),"");
//
//			if(excelYn.equals("Y")){
//				String  excelPossYn = pscmord0001Service.selectCustInfoList(paramMap);
//				gdRes.addParam("excelPossYn", excelPossYn);
//			}
//
//			// 데이터 조회
//			List<DataMap> list = pscmord0001Service.selectOrderList(paramMap);
//
//			// 조회된 데이터 가 없는 경우의 처리
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
//	        	gdRes.getHeader("ORDER_ID").addValue(map.getString("ORDER_ID"), "");
//	        	gdRes.getHeader("UP_ORDER_ID").addValue(map.getString("UP_ORDER_ID"), "");
//	        	gdRes.getHeader("STR_NM").addValue(map.getString("STR_NM"), "");
//	        	gdRes.getHeader("ORD_STS_NM").addValue(map.getString("ORD_STS_NM"),"");
//	        	gdRes.getHeader("SALE_STS_NM").addValue(map.getString("SALE_STS_NM"),"");
//	        	gdRes.getHeader("CUST_NM").addValue(map.getString("CUST_NM"),"");
//	        	gdRes.getHeader("ORDER_ITEM_AMT_SUM").addValue(map.getString("ORDER_ITEM_AMT_SUM"),"");
//	        	gdRes.getHeader("BUY_PRC").addValue(map.getString("BUY_PRC"),"");
//	        	gdRes.getHeader("DC_AMT_SUM").addValue(map.getString("DC_AMT_SUM"),"");
//	        	gdRes.getHeader("DELIVERY_AMT_SUM").addValue(map.getString("DELIVERY_AMT_SUM"),"");
//	        	gdRes.getHeader("APPLY_TOT_AMT").addValue(map.getString("APPLY_TOT_AMT"),"");
//	        	gdRes.getHeader("SETL_TYPE_NM"  ).addValue(map.getString("SETL_TYPE_NM"),"");
//	        	gdRes.getHeader("APPLY_OCUR_POINT"  ).addValue(map.getString("APPLY_OCUR_POINT"),"");
//	        	gdRes.getHeader("APPLY_COUPON_AMT"  ).addValue(map.getString("APPLY_COUPON_AMT"),"");
//	        	gdRes.getHeader("ORD_DY"  ).addValue(map.getString("ORD_DY"),"");
//	        	gdRes.getHeader("SALE_DY"  ).addValue(map.getString("SALE_DY"),"");
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


	@RequestMapping(value = "order/selectOrderList.do")
	public @ResponseBody Map selectOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

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

			String rowPerPage  = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if("".equals(param.getString("vendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("vendorId"));
				param.put("vendorId", vendorList);

			}
			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for(int l=0; openappiVendorId.size()>l; l++ ){
				if(openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())){
					param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
				}
			}


			if("06".equals(epcLoginVO.getVendorTypeCd())){

				if(((ArrayList) param.get("vendorId")).get(0).toString().indexOf("T")  < 0 ){
					param.put("vendorMode","V");
				}
			}

//			String excelYn = StringUtil.null2str(param.getString("excelYn"),"");

//			if(excelYn.equals("Y")){
//				String  excelPossYn = pscmord0001Service.selectCustInfoList(param);
//				rtnMap.put("excelPossYn", excelPossYn);
////				param.put("excelPossYn", excelPossYn);
//			}

			// 데이터 조회
			List<DataMap> list = pscmord0001Service.selectOrderList(param);

			int size = list.size();
			int totalCount = 0;
			if(size > 0 ){
				totalCount = Integer.valueOf(list.get(0).getString("TOTAL_COUNT"));
			}

			rtnMap = JsonUtils.convertList2Json((List)list, totalCount, currentPage);

			// 처리성공
 	        rtnMap.put("result", true);

		} catch(Exception e) {
			logger.error("error --> " + e.getMessage());
 	        rtnMap.put("result", false);
 	        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}


	@RequestMapping(value = "order/selectOrderListExcel.do")
	public void selectOrderListExcel(@ModelAttribute("searchVO") PSCMORD0001VO searchVO, HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		DataMap param = new DataMap(request);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 선택한 vendorId가null 이거나 일때 협력사 전체 검색
		if(searchVO.getVendorId() == null || "".equals(searchVO.getVendorId())) {
			searchVO.setVendorList(LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add(searchVO.getVendorId());
			searchVO.setVendorList(vendorList);
			param.put("vendorId", vendorList);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId().replace("[", "").replace("]", "").trim())){
				searchVO.setVendorList(LoginUtil.getVendorList(epcLoginVO));
			}
		}

//		int excelPossYn1 = 0;
//
//		String excelPossYn = pscmord0001Service.selectCustInfoList(param);
//
//		if( excelPossYn1 > 0 ){
//			throw new AlertException("배송완료 시점부터 3개월이 지난 고객정보 엑셀파일 다운로드 불가");
//		}


//		if("06".equals(epcLoginVO.getVendorTypeCd())){
//
//			if(((ArrayList) paramMap.get("vendorId")).get(0).toString().indexOf("T")  < 0 ){
//				paramMap.put("vendorMode","V");
//			}
//		}

		// 데이터 조회
		List<DataMap> list = pscmord0001Service.selectOrderListExcel((Map)param);
		JsonUtils.IbsExcelDownload((List)list, request, response);
//		return "order/PSCMORD000101";
	}


	@RequestMapping(value="/order/selectCustInfoList.json")
	public @ResponseBody Map<String, Object> selectCustInfoList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		Map<String, Object> resultMap	=	new HashMap<String, Object>();
		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if(paramMap.get("vendorId") == null  || "".equals(paramMap.get("vendorId"))) {
			paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add((String) paramMap.get("vendorId"));
			paramMap.put("vendorList",vendorList);
			paramMap.put("vendorId", vendorList);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(paramMap.get("vendorId").toString())){
				paramMap.put("vendorList", LoginUtil.getVendorList(epcLoginVO));
			}
		}

		String excelPossYn	= pscmord0001Service.selectCustInfoList(paramMap);

		resultMap.put("excelPossYn", excelPossYn);
		return resultMap;
	}


}
