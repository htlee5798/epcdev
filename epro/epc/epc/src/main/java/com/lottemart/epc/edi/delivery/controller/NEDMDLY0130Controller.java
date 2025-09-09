package com.lottemart.epc.edi.delivery.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.RFCCommonVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.delivery.model.NEDMDLY0120VO;

/**
 * 배달정보 - > 토이배달  - > 완료등록 Controller
 *
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */
@Controller
public class NEDMDLY0130Controller {
	private static final Logger logger = LoggerFactory.getLogger(NEDMDLY0130Controller.class);

	@Autowired
	private RFCCommonService rfcCommonService;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 토이배달  - > 완료등록
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/delivery/NEDMDLY0130.do", method = RequestMethod.GET)
	public String deliverReg(Locale locale, ModelMap model, HttpServletRequest request) {

		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(
				config.getString("lottemart.epc.session.key"));

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);

		model.addAttribute("paramMap", map);

		return "/edi/delivery/NEDMDLY0130";
	}
	/**
	 * 토이배달  - > 완료등록 등록
	 * @param map
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/delivery/NEDMDLY0131.do")
	public String NEDMDLY0131(@RequestParam Map<String,Object> map, Locale locale, ModelMap model, HttpServletRequest request) {
		String[] stors = map.get("storeVal").toString().split("-");

		if (stors.length>1) {
			map.put("storeVal", stors);
		}

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);

		model.addAttribute("paramMap", map);

		return "/edi/delivery/NEDMDLY0131";
	}

	/**
	 * 토이배달  - > 접수확인(텍스트파일)
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/delivery/NEDMDLY0130Text.do", method=RequestMethod.POST)
	public void NEDMDLY0130Text(NEDMDLY0120VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object>	result = new HashMap<String, Object>();

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		RFCCommonVO paramVO = new RFCCommonVO();
		paramVO.setProxyNm(vo.getProxyNm());
		paramVO.setParam(vo.getParam());

		result = rfcCommonService.rfcCall(paramVO.getProxyNm(), paramVO.getParam(),epcLoginVO.getAdminId());

		//createText(resultMap, request, response);
		/*for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			logger.debug(entry.getKey() + " " + entry.getValue());
		}*/

		JSONObject mapObj 			= new JSONObject(result.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj		= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj	= resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.

		String rtnCnt				= StringUtils.trimToEmpty(respCommonObj.getString("ZPOROWS"));
		//String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOROWS"));		//RFC 응답 성공 / 실패 여부를 담는 Key다
		logger.debug("rtnCnt----->" + rtnCnt);

		StringBuffer sb = new StringBuffer();

		sb.append("■ 배달 접수확인");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("전표번호;점포명;판매코드;상품명;희망배송일;수량;판매일자;의뢰고객명;의뢰주소;의뢰전화1;의뢰전화2;접수일자;인수고객;인수주소;인수전화1;인수전화2;배달일자;진행등록;사유");
		sb.append("\r\n");

		// 성공이 아니면 실패로 간주한다.
		if (Integer.parseInt(rtnCnt) == 1) {
			JSONObject jObj = resultObj.getJSONObject("TAB");

			sb.append(jObj.getString("SLIP_NO"));
			sb.append(";");
			sb.append(jObj.getString("STR_NM"));
			sb.append(";");
			sb.append(jObj.getString("SRCMK_CD"));
			sb.append(";");
			sb.append(jObj.getString("PROD_NM"));
			sb.append(";");
			sb.append(jObj.getString("PROM_DY"));
			sb.append(";");
			sb.append(jObj.getString("QTY"));

			sb.append(";");
			sb.append(jObj.getString("SALE_DY"));
			sb.append(";");
			sb.append(jObj.getString("CUST_NM"));
			sb.append(";");
			sb.append(jObj.getString("CUST_ADDR"));
			sb.append(";");
			sb.append(jObj.getString("CUST_TEL_NO1"));
			sb.append(";");
			sb.append(jObj.getString("CUST_TEL_NO2"));

			sb.append(";");
			sb.append(jObj.getString("ACCEPT_DY"));
			sb.append(";");
			sb.append(jObj.getString("RECV_NM"));
			sb.append(";");
			sb.append(jObj.getString("RECV_ADDR"));
			sb.append(";");
			sb.append(jObj.getString("RECV_TEL_NO1"));
			sb.append(";");
			sb.append(jObj.getString("RECV_TEL_NO2"));

			sb.append(";");
			if (!jObj.getString("DELI_END_DY").trim().equals("00000000")) {
				sb.append(jObj.getString("DELI_END_DY"));
			} else {
				sb.append("");
			}

			sb.append(";");
			String acceptFg = jObj.getString("ACCEPT_FG").trim();
			if (acceptFg.equals("0")) {
				sb.append("미접수");
			} else if (acceptFg.equals("1")) {
				sb.append("접수확인");
			} else if (acceptFg.equals("2")) {
				sb.append("배달완료");
			} else if (acceptFg.equals("3")) {
				sb.append("배달연기");
			} else if (acceptFg.equals("4")) {
				sb.append("배달실패");
			} else {
				sb.append("");
			}

			String udeliReasonFg = jObj.getString("UDELI_REASON_FG").trim();
			if (udeliReasonFg.equals("1")) {
				sb.append("재고부족");
			} else if (udeliReasonFg.equals("2")) {
				sb.append("인수거부");
			} else if (udeliReasonFg.equals("3")) {
				sb.append("주소불명");
			} else if (udeliReasonFg.equals("4")) {
				sb.append("고객부재");
			} else if (udeliReasonFg.equals("5")) {
				sb.append("착하불량");
			} else if (udeliReasonFg.equals("6")) {
				sb.append("기타연기");
			} else {
				sb.append("");
			}

			sb.append("\r\n");
		} else if (Integer.parseInt(rtnCnt) > 1) {
			JSONArray arr = resultObj.getJSONArray("TAB");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jObj = arr.getJSONObject(i);

				sb.append(jObj.getString("SLIP_NO"));
				sb.append(";");
				sb.append(jObj.getString("STR_NM"));
				sb.append(";");
				sb.append(jObj.getString("SRCMK_CD"));
				sb.append(";");
				sb.append(jObj.getString("PROD_NM"));
				sb.append(";");
				sb.append(jObj.getString("PROM_DY"));
				sb.append(";");
				sb.append(jObj.getString("QTY"));

				sb.append(";");
				sb.append(jObj.getString("SALE_DY"));
				sb.append(";");
				sb.append(jObj.getString("CUST_NM"));
				sb.append(";");
				sb.append(jObj.getString("CUST_ADDR"));
				sb.append(";");
				sb.append(jObj.getString("CUST_TEL_NO1"));
				sb.append(";");
				sb.append(jObj.getString("CUST_TEL_NO2"));

				sb.append(";");
				sb.append(jObj.getString("ACCEPT_DY"));
				sb.append(";");
				sb.append(jObj.getString("RECV_NM"));
				sb.append(";");
				sb.append(jObj.getString("RECV_ADDR"));
				sb.append(";");
				sb.append(jObj.getString("RECV_TEL_NO1"));
				sb.append(";");
				sb.append(jObj.getString("RECV_TEL_NO2"));

				sb.append(";");
				if (!jObj.getString("DELI_END_DY").trim().equals("00000000")) {
					sb.append(jObj.getString("DELI_END_DY"));
				} else {
					sb.append("");
				}

				sb.append(";");

				String acceptFg = jObj.getString("ACCEPT_FG").trim();
				if (acceptFg.equals("0")) {
					sb.append("미접수");
				} else if (acceptFg.equals("1")) {
					sb.append("접수확인");
				} else if (acceptFg.equals("2")) {
					sb.append("배달완료");
				} else if (acceptFg.equals("3")) {
					sb.append("배달연기");
				} else if (acceptFg.equals("4")) {
					sb.append("배달실패");
				} else {
					sb.append("");
				}

				String udeliReasonFg = jObj.getString("UDELI_REASON_FG").trim();
				if (udeliReasonFg.equals("1")) {
					sb.append("재고부족");
				} else if (udeliReasonFg.equals("2")) {
					sb.append("인수거부");
				} else if (udeliReasonFg.equals("3")) {
					sb.append("주소불명");
				} else if (udeliReasonFg.equals("4")) {
					sb.append("고객부재");
				} else if (udeliReasonFg.equals("5")) {
					sb.append("착하불량");
				} else if (udeliReasonFg.equals("6")) {
					sb.append("기타연기");
				} else {
					sb.append("");
				}

				sb.append("\r\n");
			}
		}

		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}

	@RequestMapping(value = "/edi/delivery/NEDMDLY0132.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String viewDeliverInfoRegistered(HttpServletRequest request, NEDMDLY0120VO vo, ModelMap model) throws Exception {

		List<String> hangmokList = Arrays.asList(vo.getFormHangmok());
		List<String> entpCdList  = Arrays.asList(vo.getFormEntpCd());
		List<String> strCdList   = Arrays.asList(vo.getFormStrCd());

		model.addAttribute("hangmokList",hangmokList);
		model.addAttribute("entpCdList" ,entpCdList);
		model.addAttribute("strCdList"  ,strCdList);

		model.addAttribute("dlyProdInfo",vo);
		return "/edi/delivery/NEDMDLY0132";
	}
}
