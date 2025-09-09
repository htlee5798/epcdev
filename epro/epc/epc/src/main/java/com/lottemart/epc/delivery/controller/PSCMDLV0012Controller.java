package com.lottemart.epc.delivery.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.delivery.model.PSCMDLV0012VO;
import com.lottemart.epc.delivery.service.PSCMDLV0012Service;
import com.lottemart.epc.order.model.PSCMORD0001VO;
import com.lottemart.epc.util.Utils;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class PSCMDLV0012Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0012Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PSCMDLV0012Service pscmdlv0012Service;


	@RequestMapping(value = "delivery/viewPartnerNoFirmsOrderList.do")
	public String selectPartnerNoFirmsOrderList(@ModelAttribute("searchVO") PSCMDLV0012VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
				if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

					String from_date = "";	 // 기간(시작)
					String to_date 	 = "";	 // 기간(끝)

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
					searchVO.setDeliStatusCode("01");
					searchVO.setSaleStsCd("11");		//결제완료

				}

				Map<String, String> map = new HashMap<String, String>();

				// 상품유형
				map.put("majorCd", "SM335");
				List<DataMap> SM335List = commonService.selectTetCodeList(map);

				// 주문상태
				map.put("majorCd", "OR002");
				List<DataMap> OR002List = commonService.selectTetCodeList(map);

				// 배송상태
				map.put("majorCd", "DE014");
				List<DataMap> DE014List = commonService.selectTetCodeList(map);

				model.addAttribute("SM335List", SM335List);	// 상품유형 코드
				model.addAttribute("OR002List", OR002List); // 주문상태 코드
				model.addAttribute("DE014List", DE014List); // 배송상태 코드

				// 배송조회
				if(searchVO.getSaleStsCd().equals("")){
					searchVO.setSaleStsCd("11");		//결제완료
				}

		return "delivery/PSCMDLV0012";
	}

	@RequestMapping(value = "delivery/selectPartnerNoFirmsOrderList.do")
	public @ResponseBody Map<String, Object> selectPartnerNoFirmsOrderList(HttpServletRequest request, HttpServletResponse response) {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			String rowPerPage  = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));
			param.put("isPageYn", "Y");

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

			if("06".equals(epcLoginVO.getVendorTypeCd())){

				if(((ArrayList<?>) param.get("vendorId")).get(0).toString().indexOf("T")  < 0 ){
					param.put("vendorMode","V");
				}
			}

			param.put("prodTypeCd", param.getString("prodTypeCd"));
			param.put("saleStsCd", param.getString("saleStsCd"));
			param.put("orderId", param.getString("orderId"));
			param.put("deliStatusCode", param.getString("deliStatusCode"));

			// 데이터 조회
			List<DataMap> list = pscmdlv0012Service.selectPartnerNoFirmsOrderList(param);

			int size = list.size();
			int totalCount = 0;
			if(size > 0 ){
				totalCount = Integer.valueOf(list.get(0).getString("TOTAL_COUNT"));
			}

			rtnMap = JsonUtils.convertList2Json((List<DataMap>)list, totalCount, currentPage);

			// 처리성공
			rtnMap.put("result", true);
		} catch(Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
		}


		return rtnMap;
	}

	@RequestMapping(value="/delivery/selectPartnerNoFirmsOrderExcel")
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

		param.put("startDate", param.getString("startDate").replaceAll("-", ""));
		param.put("endDate", param.getString("endDate").replaceAll("-", ""));

		// 선택한 vendorId가null 이거나 일때 협력사 전체 검색
		if(searchVO.getVendorId() == null || "".equals(searchVO.getVendorId())) {
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


		// 데이터 조회
		List<DataMap> list = pscmdlv0012Service.selectPartnerNoFirmsOrderList(param);

		JsonUtils.IbsExcelDownload((List<DataMap>)list, request, response);
	}
}
