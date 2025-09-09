package com.lottemart.epc.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0004Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0030Service;
import com.lottemart.epc.product.service.PSCPPRD0024Service;

@Controller("PSCPPRD0024Controller")
public class PSCPPRD0024Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0024Controller.class);

	@Autowired
	private PSCPPRD0024Service pscpprd0024Service;

	@Autowired
	private NEDMPRO0030Service nedmpro0030Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	//20181211 - 배송지 설정 수정
	@Resource(name = "pscmcom0004Service")
	private PSCMCOM0004Service pscmcom0004Service;
	//20181211 - 배송지 설정 수정

	/**
	 * 배송 폼 페이지
	 * @Description : 배송 목록 초기페이지 로딩
	 * @Method Name : deliveryView
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectDeliveryForm.do")
	public String deliveryView(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		String vendorId = request.getParameter("vendorId");

		DataMap param = new DataMap(request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		param.put("prodCd", prodCd);
		param.put("vendorId", vendorId);

		DataMap info = pscpprd0024Service.selectDeliveryInfo(param);
		DataMap vendorDlvInfo = pscpprd0024Service.selectVendorDlvInfo(param); //공통배송비정보1 조회
		param.put("deliDivnCd", "10");
		//DataMap vendorDeliInfo = pscpprd0024Service.selectVendorDeliInfo(param);  //공통배송비정보2 조회
		param.put("deliDivnCd", "20");
		paramMap.put("prodCd", prodCd);
		paramMap.put("vendorId", vendorId);
		DataMap vendorRtnDeliInfo = pscpprd0024Service.selectVendorDeliInfo(param); //공통배송비정보_반품
		DataMap newProdDetailInfo = pscpprd0024Service.selectBdlDelYn(param);
		DataMap onlineProdInfo = pscpprd0024Service.selectOnlineProdInfo(param); // 온라인상품유형 조회
		List<DataMap> infoDList = pscpprd0024Service.selectDInfoList(param);
		List<DataMap> list = pscpprd0024Service.selectDeliveryList(param);
		List<DataMap> vendorDeliInfoList = nedmpro0030Service.selectVendorDeliInfo(paramMap); //공통배송비정보2 조회
		DataMap vendorDeliInfoYn = new DataMap();

		request.setAttribute("newProdDetailInfo", newProdDetailInfo);
		request.setAttribute("onlineProdInfo", onlineProdInfo);
		request.setAttribute("list", list);
		request.setAttribute("infoDList", infoDList);
		request.setAttribute("deliveryInfo", info);
		request.setAttribute("vendorDlvInfo", vendorDlvInfo);
		request.setAttribute("vendorDeliInfoList", vendorDeliInfoList);
		request.setAttribute("vendorRtnDeliInfo", vendorRtnDeliInfo);

		if (vendorDeliInfoList.size() == 2) {
			vendorDeliInfoYn.put("DELI_INFO_YN", "Y");
		} else {
			vendorDeliInfoYn.put("DELI_INFO_YN", "N");
		}
		request.setAttribute("vendorDeliInfoYn", vendorDeliInfoYn);

		//20181211 - 배송지 설정 수정

		List<DataMap> vendorAddrlist = pscmcom0004Service.selectVendorAddrList(paramMap); //출고지, 반품/교환지 주소 조회
		request.setAttribute("vendorAddrlist", vendorAddrlist);

		String vendorAddrInfoCnt = pscpprd0024Service.selectVendorAddrInfoCnt(paramMap);
		request.setAttribute("vendorAddrInfoCnt", vendorAddrInfoCnt);
		//20181211 - 배송지 설정 수정

		//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정

		return "product/internet/PSCPPRD002401";
	}

	/**
	 * 배송이력조회 폼 페이지
	 * @Description : 배송 목록 초기페이지 로딩
	 * @Method Name : deliveryViewSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectDeliveryInfoFrame.do")
	public String deliveryViewSearch(HttpServletRequest request) throws Exception {
		DataMap param = new DataMap(request);

		DataMap info = pscpprd0024Service.selectDeliveryInfo(param);
		DataMap vendorDlvInfo = pscpprd0024Service.selectVendorDlvInfo(param); //공통배송비정보1 조회
		DataMap vendorDeliInfo = pscpprd0024Service.selectVendorDeliInfo(param); //공통배송비정보2 조회

		request.setAttribute("vendorDlvInfo", vendorDlvInfo);
		request.setAttribute("vendorDeliInfo", vendorDeliInfo);

		return "product/internet/PSCPPRD002402";
	}

	@ResponseBody
	@RequestMapping("product/selectDeliverySearch.do")
	public Map<String, Object> selectDeliverySearch1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("================selectDeliverySearch Start================");

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);
			String rowsPerPage = StringUtil.null2str((String) param.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String) param.get("currentPage")) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("prodCd", request.getParameter("prodCd"));
			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));

			List<DataMap> list = pscpprd0024Service.selectDeliveryList(param);

			// json
			JSONArray jArray = new JSONArray();
			if (list != null) {
				jArray = (JSONArray) JSONSerializer.toJSON(list);
			}

			String jStr = "{Data:" + jArray + "}";
			rtnMap.put("ibsList", jStr);

			// 조회된 데이터가 없는 경우
			if (jArray.isEmpty()) {
				rtnMap.put("result", false);
			}
			//성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);
		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		logger.debug("================selectDeliverySearch End================");
		return rtnMap;
	}

	@RequestMapping("product/deliveryInfoSave.do")
	public String deliveryInfoSave(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		String vendorId = request.getParameter("vendorId");

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		DataMap paramMap = new DataMap(request);
		if ("".equals(epcLoginVO.getAdminId()) || epcLoginVO.getAdminId() == null) {
			epcLoginVO.setAdminId(epcLoginVO.getRepVendorId());
		}
		paramMap.put("regId", epcLoginVO.getAdminId());
		paramMap.put("modId", epcLoginVO.getAdminId());

		if (!"Y".equals(paramMap.getString("condUseYn")) && "01".equals(paramMap.getString("deliKindCdSel"))) {
			String deliCondAmt = paramMap.getString("deliCondAmt");
			if (deliCondAmt != null && !"".equals(deliCondAmt)) {
				if(deliCondAmt.length() > 10) {
					throw new AlertException("상품금액별 차등의 기준 구매금액은 최대 10자리까지만 가능합니다.");
				}
			}
		}

		try {
			pscpprd0024Service.updatePrdEvtCopy(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		request.setAttribute("prodCd", prodCd);
		request.setAttribute("vendorId", vendorId);

		return "redirect:/product/selectDeliveryForm.do?prodCd=" +prodCd+"&vendorId="+vendorId;
	}

	@RequestMapping("product/deliverySave.do")
	public @ResponseBody JSONObject deliverySave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			resultCnt = pscpprd0024Service.deliverySave(request);

			// 처리 결과
			if (resultCnt > 0) {
				jObj.put("Code", 1);
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Message", resultCnt + "건의 " + message);

			} else {
				jObj.put("Code", 0);
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}
		return JsonUtils.getResultJson(jObj);
	}

	//20181211 - 배송지 설정 수정
	@RequestMapping("product/deliveryAddressInfoSave.do")
	public String deliveryAddressInfoSave(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		String vendorId = request.getParameter("vendorId");

		String sessionKey = config.getString("lottemart.epc.session.key");

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		DataMap paramMap = new DataMap(request);
		paramMap.put("regId", epcLoginVO.getRepVendorId());
		paramMap.put("modId", epcLoginVO.getRepVendorId());

		try {
			pscpprd0024Service.updateDeliveryAddressInfo(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		request.setAttribute("prodCd", prodCd);
		request.setAttribute("vendorId", vendorId);

		return "redirect:/product/selectDeliveryForm.do?prodCd=" + prodCd + "&vendorId=" + vendorId;
	}
	//20181211 - 배송지 설정 수정

}