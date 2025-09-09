package com.lottemart.epc.delivery.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.delivery.model.PSCMDLV0009VO;
import com.lottemart.epc.delivery.service.PSCMDLV0009Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMDLV0009Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0009Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMDLV0009Service pscmdlv0009Service;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonService commonService;

	//교환/반품/취소 목록
	@RequestMapping(value = "delivery/viewPartnerReturnList.do")
	public String viewPartnerReturnList(@ModelAttribute("searchVO") PSCMDLV0009VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		/*if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}*/

		request.setAttribute("epcLoginVO", epcLoginVO);

		String from_date = ""; // 기간(시작)
		String to_date = ""; // 기간(끝)

		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, 0);
		NowDate2.add(Calendar.DATE, -7);

		String today_date = Utils.formatDate(NowDate.getTime(), "yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(), "yyyy-MM-dd");

		// 초최 오픈시 Default 값 세팅
		if (from_date == null || "".equals(from_date)) {
			from_date = today_date2;
		}
		if (to_date == null || "".equals(to_date)) {
			to_date = today_date;
		}

		searchVO.setStartDate(from_date);
		searchVO.setEndDate(to_date);
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		searchVO.setMajorCd("OR001");
		List<DataMap> OR001List = pscmdlv0009Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("OR002");
		List<DataMap> OR002List = pscmdlv0009Service.getTetCodeList(searchVO);

		model.addAttribute("OR001List", OR001List); //주문/취소/반품구분 코드
		model.addAttribute("OR002List", OR002List); //주문상태 코드

		model.addAttribute("searchVO", searchVO);
		return "delivery/PSCMDLV0009";
	}

	//교환/반품/취소 목록 조회
	@RequestMapping(value = "delivery/selectPartnerReturnList.do")
	public @ResponseBody Map selectPartnerReturnList(@ModelAttribute("searchVO") PSCMDLV0009VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		try {

			DataMap param = new DataMap(request);

			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);
			// param.put("startRecord", (currPage-1) * rowPage + 1);
			// param.put("endRecord", currPage * rowPage);

			// 페이징 관련 변수
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if ("".equals(param.getString("vendorId")) || epcLoginVO.getRepVendorId().equals(param.getString("vendorId"))) {
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

			if ("06".equals(epcLoginVO.getVendorTypeCd())) {
				if (((ArrayList) param.get("vendorId")).get(0).toString().indexOf("T") < 0) {
					param.put("vendorMode", "V");
				}
			}

			List<DataMap> list = pscmdlv0009Service.selectPartnerReturnList(param);
			int size = list.size();
			int totalCnt = 0;
			if (size > 0) {

				totalCnt = list.get(0).getInt("TOTAL_COUNT");
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, currentPage);
			// 처리성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping(value = "delivery/selectPartnerReturnListExcel.do")
	public void selectPartnerReturnListExcel(@ModelAttribute("searchVO") PSCMDLV0009VO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		/*if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}*/

		request.setAttribute("epcLoginVO", epcLoginVO);

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		DataMap paramMap = new DataMap();

		paramMap.put("startDate", searchVO.getStartDate().replaceAll("-", ""));
		paramMap.put("endDate", searchVO.getEndDate().replaceAll("-", ""));
		paramMap.put("ordRtnDivnCd", searchVO.getOrdRtnDivnCd());
		paramMap.put("ordStsCd", searchVO.getOrdStsCd());
		paramMap.put("searchType", searchVO.getSearchType());
		paramMap.put("searchContent", searchVO.getSearchContent());
		paramMap.put("currentPage", "1");
		paramMap.put("rowsPerPage", "65000");

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if ("".equals(searchVO.getVendorId()) || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId())) {
			paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add(searchVO.getVendorId());
			paramMap.put("vendorId", vendorList);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId().replace("[", "").replace("]", "").trim())) {
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			}
		}

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if (((ArrayList) paramMap.get("vendorId")).get(0).toString().indexOf("T") < 0) {
				paramMap.put("vendorMode", "V");
			}
		}

		List<DataMap> list = pscmdlv0009Service.selectPartnerReturnList(paramMap);

		model.addAttribute("list", list);
		JsonUtils.IbsExcelDownload((List) list, request, response);
	}

	/**
	 * Desc : 회수처리
	 * @Method Name : updateOrdStsCd
	 * @param request,response
	 * @throws Exception
	 * @return gdRes
	 */
	@RequestMapping("/delivery/updateWdrwProc.do")
	public @ResponseBody JSONObject updateWdrwProc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jObj = new JSONObject();

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		try {
			// Sheet에서 수정으로 체크된 게시물의 게시물 번호
			String[] orderId = request.getParameterValues("ORDER_ID");
			String[] orderItemSeq = request.getParameterValues("ORDER_ITEM_SEQ");
			String[] ordStsCd = request.getParameterValues("ORD_STS_CD");
			String[] deliveryId = request.getParameterValues("DELIVERY_ID");
			String[] ecOrderYn = request.getParameterValues("EC_ORDER_YN");

			// 수정할 게시물 목록 가공
			List<PSCMDLV0009VO> orderItemList = new ArrayList<PSCMDLV0009VO>();

			for (int i = 0; i < orderId.length; i++) {
				PSCMDLV0009VO bean = new PSCMDLV0009VO();
				bean.setOrderId(orderId[i]);
				bean.setOrderItemSeq(orderItemSeq[i]);
				bean.setEcOrderYn(ecOrderYn[i]);

				if (!"Y".equals(ecOrderYn[i])) {
					if (ordStsCd[i].equals("40")) { // 반품접수
						bean.setDeliStatusCd("64"); // 반품회수완료
					} else if (ordStsCd[i].equals("50")) { // 교환접수
						bean.setDeliStatusCd("74"); // 교환회수완료
					}
				} else {
					if (ordStsCd[i].equals("40")) { // 반품접수
						bean.setDeliStatusCd("66"); // 반품회수완료
					} else if (ordStsCd[i].equals("50")) { // 교환접수
						bean.setDeliStatusCd("77"); // 교환회수완료
					}
				}

				bean.setDeliveryId(deliveryId[i]);

				if (epcLoginVO.getAdminId() != null && !epcLoginVO.getAdminId().equals("")) {
					bean.setModId(epcLoginVO.getAdminId());
				} else {
					bean.setModId(epcLoginVO.getRepVendorId());
				}
				orderItemList.add(bean);
			}

			// 회수처리
			int resultCnt = 0;
			resultCnt = pscmdlv0009Service.updateTorOrderItem(orderItemList);

			// 처리 결과 메세지 생성
			if (resultCnt > 0) {
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			} else {
				jObj.put("Code", 0);
				jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}
		return JsonUtils.getResultJson(jObj);
	}

}