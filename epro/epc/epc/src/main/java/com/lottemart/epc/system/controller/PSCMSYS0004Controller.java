/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2016. 05 31. 오후 2:30:50
 * @author      : kslee 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.system.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.epc.system.model.PSCMSYS0002VO;
import com.lottemart.epc.system.model.PSCMSYS0003VO;
import com.lottemart.epc.system.model.PSCMSYS0004VO;
import com.lottemart.epc.system.service.PSCMSYS0004Service;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

/**
 * @Class Name : PSCMSYS0004Controller
 * @Description : 업체정보관리 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:30:50 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PSCMSYS0004Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0004Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private PSCMSYS0004Service pscmsys0004Service;
	// 첨부파일 관련 2014.10.06 박지혜 추가

	/**
	 * Desc : 업체정보관리 목록
	 * 
	 * @Method Name : selectVendorInfoMgr
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/system/selectVendorInfoMgr.do")
	public String selectVendorInfoMgr(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		//Map rtnMap = new HashMap<String, Object>();

		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		List<DataMap> codeList1 = commonCodeService.getCodeList("SM065"); // 거래유형 공통코드 조회
		request.setAttribute("codeList1", codeList1);

		List<DataMap> codeList2 = commonCodeService.getCodeList("VEN20"); // 업종 공통코드 조회
		request.setAttribute("codeList2", codeList2);

		List<DataMap> codeList3 = commonCodeService.getCodeList("SM336"); // 주소종류 공통코드 조회
		request.setAttribute("codeList3", codeList3);

		List<DataMap> codeList4 = commonCodeService.getCodeList("EPC03"); // 담당자 구분 공통코드 조회
		request.setAttribute("codeList4", codeList4);

		String vendorId = StringUtil.null2str(request.getParameter("vendorId"));
		if ("".equals(vendorId)) {
			vendorId = epcLoginVO.getRepVendorId();
		}

		// 업체정보
		PSCMSYS0003VO vendorInfo = pscmsys0004Service.selectVendorInfoView(vendorId);
		HttpSession session = request.getSession();
		logger.error("SessionId = [" + session.getId() + "] | SelectInfo: " + vendorInfo.toString());

		request.setAttribute("vData", vendorInfo);

		return "system/PSCMSYS0004";
	}

	/**
	 * Desc : 업체정보관리_업체정보 수정
	 * @Method Name : updateVendor
	 * @param request,response
	 * @throws Exception
	 * @return gdRes
	 */
	@RequestMapping("/system/updateVendorInfo.do")
	public String updateVendorInfo(@ModelAttribute("vo") PSCMSYS0003VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		vo.setRegId(epcLoginVO.getCono()[0]); 

		int resultCnt = 0;
		try {
			HttpSession session = request.getSession();
			logger.error("SessionId = [" + session.getId() + "] | UpdateInfo: " + vo.toString());
			resultCnt = pscmsys0004Service.updateVendorInfo(vo);
			model.addAttribute("msg", "수정되었습니다.");
			model.addAttribute("vandorId", vo.getVendorId());
		} catch(Exception e) {
			model.addAttribute("msg", e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}

		PSCMSYS0003VO vendorViewInfo = pscmsys0004Service.selectVendorInfoView(vo.getVendorId());

		// API 연동 (BOS -> 통합BO API)
		if (resultCnt > 0 && !vo.getVendorId().startsWith("T") && "KM05".equals(vendorViewInfo.getVendorKindCd())) {
			try {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				List<String> traderCd = new ArrayList<String>();
				traderCd.add(vo.getVendorId());
				reqMap.put("traderType", "VENDOR");
				reqMap.put("modifyTraderCds", traderCd);

				RestAPIUtil rest = new RestAPIUtil();

				String result = rest.sendRestCall(RestConst.API_URL_TRADER_LOWRANK_MODIFY, HttpMethod.POST, reqMap, 5000, true);
				logger.error("API_URL_TRADER_LOWRANK_MODIFY Call Result = " + result);

			} catch (Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(errors.toString());
			}
		}
		return "common/mainMessageResult";
	}
	
	/**
	 * Desc : 업체담당자조회
	 * @Method Name : selectVendorUserList
	 * @param request,response
	 * @throws Exception
	 * @return rtnMap
	 */
	@RequestMapping(value = "/system/selectVendorUserList.do")
	public @ResponseBody Map selectVendorUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		try {
			List<PSCMSYS0002VO> list = pscmsys0004Service.selectVendorUserList((Map) paramMap);
			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 업체주소조회
	 * @Method Name : selectVendorUserList
	 * @param request,response
	 * @throws Exception
	 * @return rtnMap
	 */
	@RequestMapping(value = "/system/selectVendorAddrList.do")
	public @ResponseBody Map selectVendorAddrList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		try {
			// paramMap.put("vendorId", paramMap.getString("vendorId"));
			List<PSCMSYS0004VO> list = pscmsys0004Service.selectVendorAddrList((Map) paramMap);
			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 업체기준배송비조회
	 * @Method Name : selectVendorDeliAmtList
	 * @param request,response
	 * @throws Exception
	 * @return rtnMap
	 */
	@RequestMapping(value = "/system/selectVendorDeliAmtList.do")
	public @ResponseBody Map selectVendorDeliAmtList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		try {
			List<PSCMSYS0001VO> list = pscmsys0004Service.selectVendorDeliAmtList((Map) paramMap);
			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 업체담당자 저장
	 * @Method Name : vendorUserListSave
	 * @param request,response
	 * @throws Exception
	 * @return gdRes
	 */
	@RequestMapping("system/vendorUserListSave.do")
	public @ResponseBody JSONObject vendorUserListSave(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			resultCnt = pscmsys0004Service.vendorUserListSave(request);

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

	/**
	 * Desc : 업체주소 저장
	 * @Method Name : vendorAddrListSave
	 * @param request,response
	 * @throws Exception
	 * @return gdRes
	 */
	@RequestMapping("system/vendorAddrListSave.do")
	public @ResponseBody JSONObject vendorAddrListSave(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			resultCnt = pscmsys0004Service.vendorAddrListSave(request);

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

	/**
	 * Desc : 업체기준배송비 저장
	 * @Method Name : vendorDeliListSave
	 * @param request,response
	 * @throws Exception
	 * @return gdRes
	 */
	@RequestMapping("system/vendorDeliListSave.do")
	public @ResponseBody JSONObject vendorDeliListSave(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			String deliDivnCd = "";
			String [] deliDivnCdArr = request.getParameterValues("DELI_DIVN_CD");
			if (deliDivnCdArr != null && deliDivnCdArr.length > 0) {
				deliDivnCd = deliDivnCdArr[0];
			}
			if ("10".equals(deliDivnCd)) {
				if (request.getParameterValues("DELI_BASE_MAX_AMT") == null || request.getParameterValues("DELI_BASE_MAX_AMT").length == 0) {
					throw new AlertException("기준최대금액 값을 입력해주세요.");
				}
				String[] deliBaseMaxAmtArr = request.getParameterValues("DELI_BASE_MAX_AMT");
				if (deliBaseMaxAmtArr != null && deliBaseMaxAmtArr.length > 0) {
					for (int i = 0; i < deliBaseMaxAmtArr.length; i++) {
						if (StringUtils.isBlank(deliBaseMaxAmtArr[i])) {
							throw new AlertException("기준최대금액 값을 입력해주세요.");
						}
					}
				}
			}
			if ("10".equals(deliDivnCd) || "20".equals(deliDivnCd)) {
				if (request.getParameterValues("DELIVERY_AMT") == null || request.getParameterValues("DELIVERY_AMT").length == 0) {
					throw new AlertException("배송비 값을 입력해주세요.");
				}
				String[] deliveryAmtArr = request.getParameterValues("DELIVERY_AMT");
				if (deliveryAmtArr != null && deliveryAmtArr.length > 0) {
					for (int i = 0; i < deliveryAmtArr.length; i++) {
						if (StringUtils.isBlank(deliveryAmtArr[i])) {
							throw new AlertException("배송비 값을 입력해주세요.");
						}
					}
				}
			}

			resultCnt = pscmsys0004Service.vendorDeliListSave(request);

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

	/**
	 * Desc : 업체 기준배송비이력 목록
	 * 
	 * @Method Name : viewDeliChgHistPopUp
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/system/viewDeliChgHistPopUp.do")
	public String viewDeliChgHistPopUp(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String vendorId = StringUtil.null2str(SecureUtil.stripXSS(request.getParameter("vendorId")));
		String deliDivnCd = StringUtil.null2str(SecureUtil.stripXSS(request.getParameter("deliDivnCd")));

		// 배송정책 공통코드 조회
		List<DataMap> codeList4 = commonCodeService.getCodeList("SM347");

		request.setAttribute("codeList4", codeList4);
		request.setAttribute("vendorId", vendorId);
		request.setAttribute("deliDivnCd", deliDivnCd);
		

		return "system/PSCMSYS000401";
	}

}
