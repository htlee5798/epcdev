/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.calculation.controller;

//추가
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.calculation.model.PSCMCAL0003SearchVO;
import com.lottemart.epc.calculation.service.PSCMCAL0003Service;
import com.lottemart.epc.common.model.EpcLoginVO;




/**
 * @Class Name : PSCMCAL0003Controller
 * @Description : 배송료 정산 목록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:09:43 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMCAL0003Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMCAL0003Service pscmcal0003Service;
	

	/**
	 * Desc : 배송료정산 목록
	 * 
	 * @Method Name : selectDeliverySettleCostsCalculateList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/calculation/selectDeliverySettleCostsCalculateList.do")	
	public String selectBoardList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		return "calculation/PSCMCAL0003";
	}
	
	/**
	 * Desc : 배송료정산 목록을 조회하는 메소드
	 * @Method Name : selectDeliverySettleCostsCalculateSearch
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
//	@RequestMapping(value = "/calculation/selectDeliverySettleCostsCalculateSearch.do")
//	public String selectDeliverySettleCostsCalculateSearch(HttpServletRequest request,
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
//			config.getString("count.row.per.page");
//
//			DataMap paramMap = new DataMap();
//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage",StringUtil.null2str(gdReq.getParam("rowsPerPage"),config.getString("count.row.per.page")));
//			paramMap.put("searchMonth", gdReq.getParam("searchMonth"));
//			paramMap.put("vendorId", gdReq.getParam("vendorId"));
//			paramMap.put("venCds", epcLoginVO.getVendorId());
//
//			// 데이터 조회
//			List<DataMap> list = pscmcal0003Service.selectDeliverySettleCostsCalculateList(paramMap);
//			                                 
//			DataMap sum = pscmcal0003Service.selectDeliverySettleCostsCalculateSum(paramMap);
//			String sumPayMm = "";
//			String sumDeliveryAmt = "0";
//			
//			int size = list.size();
//			if(sum != null){
//				sumPayMm = sum.getString("SUM_PAY_MM");
//				sumDeliveryAmt = sum.getString("SUM_DELIVERY_AMT");
//			} 
//
//			gdRes.addParam("sumPayMm", sumPayMm);
//			gdRes.addParam("sumDeliveryAmt", sumDeliveryAmt);
//			
//			// 조회된 데이터 가 없는 경우의 처리
//			if (list == null || size == 0) {
//				gdRes.setStatus("false");
//				request.setAttribute("wizeGridResult", gdRes);
//				return "common/wiseGridResult";
//			}
//
//			// GridData 셋팅
//			for (int i = 0; i < size; i++) {
//				DataMap map = list.get(i);
//
//				gdRes.getHeader("rankNum").addValue(map.getString("RANK_NUM"), "");
//				gdRes.getHeader("payMm").addValue(map.getString("PAY_MM"), "");
//				gdRes.getHeader("islndRegnChek").addValue(map.getString("ISLND_REGN_CHEK"), "");
//				gdRes.getHeader("orderId").addValue(map.getString("ORDER_ID"), "");
//				gdRes.getHeader("upOrderId").addValue(map.getString("UP_ORDER_ID"), "");
//				gdRes.getHeader("ordDy").addValue(map.getString("ORD_DY"), "");
//				gdRes.getHeader("ordStsNm").addValue(map.getString("ORD_STS_NM"), "");
//				gdRes.getHeader("saleStsNm").addValue(map.getString("SALE_STS_NM"), "");
//				gdRes.getHeader("ordAmt").addValue(map.getString("ORD_AMT"), "");
//				gdRes.getHeader("custNm").addValue(map.getString("CUST_NM"), "");
//				gdRes.getHeader("deliveryAmt").addValue(map.getString("DELIVERY_AMT"), "");
//			}
//
//			String totalCount = list.get(0).getString("TOTAL_COUNT");
//
//			// 페이징 변수
//			gdRes.addParam("totalCount", totalCount);
//			gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//			
//			gdRes.setStatus("true");
//			
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
	
	
	@RequestMapping(value = "/calculation/selectDeliverySettleCostsCalculateSearch.do")
	public @ResponseBody Map selectDeliverySettleCostsCalculateSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		
		Map rtnMap = new HashMap<String, Object>();
		
		try {
			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			config.getString("count.row.per.page");
			
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
			
			param.put("vendorId", param.getString("vendorId"));
			param.put("venCds", epcLoginVO.getVendorId());
			
			// 데이터 조회
			List<PSCMCAL0003SearchVO> list = pscmcal0003Service.selectDeliverySettleCostsCalculateList(param);
			
			DataMap sum = pscmcal0003Service.selectDeliverySettleCostsCalculateSum(param);
			String sumPayMm = "";
			String sumDeliveryAmt = "0";
			
			int size = list.size();
			int totalCount = 0;
			if(sum != null){
				sumPayMm = sum.getString("SUM_PAY_MM");
				sumDeliveryAmt = sum.getString("SUM_DELIVERY_AMT");
			} 
			
			param.put("sumPayMm", sumPayMm);
			param.put("sumDeliveryAmt", sumDeliveryAmt);
			
			if(size > 0 ){
				totalCount = Integer.valueOf(list.get(0).getTotalCount());
			}

			rtnMap = JsonUtils.convertList2Json((List)list, totalCount, currentPage);
			
			// 처리성공
 	        rtnMap.put("result", true);
 	        rtnMap.put("sumPayMm", sumPayMm);
	        rtnMap.put("sumDeliveryAmt", sumDeliveryAmt);
			
		} catch (Exception e) {
//			logger.error("error --> " + e.getMessage());
 	        rtnMap.put("result", false);
 	        rtnMap.put("Message", e.getMessage());
		}
		
		return rtnMap;
	}
	
  	public static int getLastDayOfMonth(String year, String month)	{
		Calendar nextMonthFirstDay = Calendar.getInstance();
		nextMonthFirstDay.set(Integer.parseInt(year), Integer.parseInt(month), 1);
		
		long l = nextMonthFirstDay.getTime().getTime() - 86400000;
		
		Calendar currentMonthLastDay = Calendar.getInstance();
		currentMonthLastDay.setTime( new Date(l) );
		
		return currentMonthLastDay.get(Calendar.DAY_OF_MONTH);
  	}
}
