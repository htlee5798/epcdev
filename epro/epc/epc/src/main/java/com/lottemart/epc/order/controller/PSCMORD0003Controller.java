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
import com.lottemart.epc.order.model.PSCMORD0003VO;
import com.lottemart.epc.order.service.PSCMORD0003Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMORD0003Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMORD0003Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMORD0003Service pscmord0003Service;

	@Autowired
	private CommonService commonService;

	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "order/viewSocialOrderList.do")
	public String viewSocialOrderList(@ModelAttribute("searchVO") PSCMORD0003VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

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
		List<DataMap> strCdList = pscmord0003Service.selectAllOnlineStore();
		model.addAttribute("strCdList", strCdList);

		// 주문상태
		searchVO.setMajorCd("OR002");
		List<DataMap> ordStsCdList = pscmord0003Service.selectCodeList(searchVO);
		model.addAttribute("ordStsCdList", ordStsCdList);

		// 결제유형
		searchVO.setMajorCd("OR008");
		List<DataMap> setlTypeCdList = pscmord0003Service.selectCodeList(searchVO);
		model.addAttribute("setlTypeCdList", setlTypeCdList);

		// 매출상태
		searchVO.setMajorCd("OR003");
		List<DataMap> saleStsCdList = pscmord0003Service.selectCodeList(searchVO);
		model.addAttribute("saleStsCdList", saleStsCdList);

        model.addAttribute("searchVO", searchVO);
		return "order/PSCMORD0003";
	}

	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value = "order/selectSocialOrderList.do")
	public @ResponseBody Map selectSocialOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
			if("".equals(param.getString("vendorId")) || epcLoginVO.getRepVendorId().equals(param.getString("vendorId"))) {
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


//			String excelYn = StringUtil.null2str(param.getString("excelYn"),"");
//
//			if(excelYn.equals("Y")){
//				String excelPossYn = pscmord0003Service.selectCustInfo(param);
//				rtnMap.put("excelPossYn", excelPossYn);
//			}

			// 데이터 조회
			List<DataMap> list = pscmord0003Service.selectOrderList(param);

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

	@RequestMapping(value = "order/selectSocialOrderListExcel.do")
	public void selectSocialOrderListExcel(@ModelAttribute("searchVO") PSCMORD0003VO searchVO, HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {


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

//		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
//		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 선택한 vendorId가null 일때 협력사 전체 검색
		if(searchVO.getVendorId() == null ||"".equals(searchVO.getVendorId())) {
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

		// 데이터 조회
		List<DataMap> list = pscmord0003Service.selectOrderListExcel((Map)param);
		JsonUtils.IbsExcelDownload((List)list, request, response);
//		return "order/PSCMORD000301";
	}

	// 마스킹 관련 메뉴제거 및 URL 차단 - 2021-01-13
	//@RequestMapping(value="/order/selectCustInfo.json")
	public @ResponseBody Map<String, Object> selectCustInfo(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		Map<String, Object> resultMap	=	new HashMap<String, Object>();

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if(paramMap.get("vendorId") == null || epcLoginVO.getRepVendorId().equals(paramMap.get("vendorId")) || "".equals(paramMap.get("vendorId"))) {
			paramMap.put("vendorList", LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add((String) paramMap.get("vendorId"));
			paramMap.put("vendorList",vendorList);
			paramMap.put("vendorId", vendorList);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(paramMap.get("vendorId").toString().replace("[", "").replace("]", "").trim())){
				paramMap.put("vendorList", LoginUtil.getVendorList(epcLoginVO));
			}
		}


		String excelPossYn	= pscmord0003Service.selectCustInfo(paramMap);

		resultMap.put("excelPossYn", excelPossYn);
		return resultMap;
	}

}
