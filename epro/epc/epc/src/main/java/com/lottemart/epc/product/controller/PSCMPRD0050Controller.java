package com.lottemart.epc.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.product.service.PSCMPRD0050Service;

/**
 * @Class Name : PSCMPRD0050Controller
 * @Description : 증정품관리 Controller 클래스
 * @Modification Information
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.06.07   projectBOS32	신규생성
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMPRD0050Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0050Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMPRD0050Service pscmprd0050Service;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonService commonService;

	/**
	 * Desc : 증정품 조회 폼
	 *
	 * @Method Name : selectGiftView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectGiftView.do")
	public String selectGiftView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0050";
	}

	/**
	 * Desc : 추가구성품 목록을 조회하는 메소드
	 * @Method Name : selectSuggestionSearch
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectGiftList.do")
	public @ResponseBody Map selectGiftListSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");
			String prodCd = paramMap.getString("prodCd");

			paramMap.put("prodCdArr", prodCd.split(","));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (paramMap.getString("vendorId").length() == 0
					|| epcLoginVO.getRepVendorId().equals(paramMap.get("vendorId"))) {
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				String venderId[] = { paramMap.getString("vendorId") };
				paramMap.put("vendorId", venderId);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(request.getParameter("vendorId"))) {
					paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
				}
			}

			// 데이터 조회
			List<DataMap> list = pscmprd0050Service.selectGiftList(paramMap);
			rtnMap = JsonUtils.convertList2Json((List) list, -1, currentPage);

			rtnMap.put("result", true);
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	@RequestMapping("product/updateBatchGift.do")
	public @ResponseBody JSONObject updateBatchGift(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			resultCnt = pscmprd0050Service.updateBatchGift(request);

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
}
