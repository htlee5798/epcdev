package com.lottemart.epc.edi.buy.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.buy.model.NEDMBUY0110VO;
import com.lottemart.epc.edi.comm.model.RFCCommonVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.commonUtils.commonUtil;
/**
 * 매입정보 - > 입고 거부 상품  - > 센터 입고 거부 상품   Controller
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
public class NEDMBUY0110Controller {

	@Autowired
	private RFCCommonService rfcCommonService;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 매입정보 - > 입고 거부 상품  - > 센터 입고 거부 상품  첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0110.do")
	public String doInit(Locale locale, ModelMap model) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String tmp = "1";

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		map.put("measure", tmp);

		model.addAttribute("paramMap", map);

		return "/edi/buy/NEDMBUY0110";
	}

	/**
	 * 매입정보 - > 입고 거부 상품  - > 센터 입고 거부 상품  조회
	 * @param NEDMBUY0110VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/buy/selectNEDMBUY0110.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectNEDMBUY0110(ModelMap model, @RequestBody NEDMBUY0110VO paramVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*Map<String, Object> resultMap = new HashMap<String, Object>();

		logger.debug("----->" + paramVO.getParam());
		StringBuffer sb = new StringBuffer();
		sb = defService.selectNEDMBUY0110(paramVO);

		resultMap.put("result", sb.toString());*/
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		return rfcCommonService.rfcCall(paramVO.getProxyNm(), paramVO.getParam(),epcLoginVO.getAdminId());
	}

	/**
	 * 매입정보 - > 입고 거부 상품  - > 센터 입고 거부 상품  조회 popup 페이지
	 * @param NEDMBUY0110VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0111.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String selectBadProdPopup(@RequestParam Map<String,Object> map, ModelMap model) throws Exception {

		model.addAttribute("paramMap",map);

		return "/edi/buy/NEDMBUY0111";
	}


	@RequestMapping(value = "/edi/buy/NEDMBUY0110Text.do", method=RequestMethod.POST)
	public void NEDMBUY0110Text(NEDMBUY0110VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object>	result = new HashMap<String, Object>();

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		RFCCommonVO paramVO = new RFCCommonVO();
		paramVO.setProxyNm(vo.getProxyNm());
		paramVO.setParam(vo.getParam());

		result = rfcCommonService.rfcCall(paramVO.getProxyNm(), paramVO.getParam(),epcLoginVO.getAdminId());

		//createText(resultMap, request, response);
		/*for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}*/

		JSONObject mapObj 			= new JSONObject(result.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj		= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj	= resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.

		String rtnCnt				= StringUtils.trimToEmpty(respCommonObj.getString("ZPOROWS"));
		//String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOROWS"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

		StringBuffer sb = new StringBuffer();

		sb.append("■ 매입정보(입고거부상품)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("의뢰일자;상품코드;상품명;발생일자;발생지점;발생유형;조치구분");
		sb.append("\r\n");

		// 성공이 아니면 실패로 간주한다.
		if (Integer.parseInt(rtnCnt) == 1) {
			JSONObject jObj = resultObj.getJSONObject("TAB");

			sb.append(jObj.getString("SEND_DATE"));
			sb.append(";");
			sb.append(jObj.getString("PROD_CD"));
			sb.append(";");
			sb.append(jObj.getString("PROD_NM"));
			sb.append(";");
			sb.append(jObj.getString("REG_DY"));
			sb.append(";");
			sb.append(jObj.getString("STR_NM"));
			sb.append(";");
			sb.append(jObj.getString("DENY_FG"));
			sb.append(";");


			if (StringUtils.trimToEmpty(jObj.getString("DENY_FG")).equals("C")) {
				sb.append("조치");
			} else {
				sb.append("미조치");
			}

			sb.append(";");
			sb.append("\r\n");
		} else if (Integer.parseInt(rtnCnt) > 1) {
			JSONArray arr = resultObj.getJSONArray("TAB");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jObj = arr.getJSONObject(i);
				//for (int j = 0; j < jObj.names().length(); j++) {
					//System.out.println("key = " + jObj.names().getString(j) + " value = " + jObj.get(jObj.names().getString(j)));

				sb.append(jObj.getString("SEND_DATE"));
				sb.append(";");
				sb.append(jObj.getString("PROD_CD"));
				sb.append(";");
				sb.append(jObj.getString("PROD_NM"));
				sb.append(";");
				sb.append(jObj.getString("REG_DY"));
				sb.append(";");
				sb.append(jObj.getString("STR_NM"));
				sb.append(";");
				sb.append(jObj.getString("DENY_FG"));
				sb.append(";");

				if (StringUtils.trimToEmpty(jObj.getString("DENY_FG")).equals("C")) {
					sb.append("조치");
				} else {
					sb.append("미조치");
				}

				sb.append(";");
				sb.append("\r\n");
				//}
				//System.out.println(o);
			}
		}

		sb.append("------------------------------------------------------------------------------------");

		commonUtil.createTextFile(request, response, sb);
	}

	/**
	 * 협력사 메인페이지 - > EDI 클릭 시  - > 입하거부상품 등록 조회  popup 페이지
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0112.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String mainBadProdPopup(HttpServletRequest request, ModelMap model) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("VEN_CDS",epcLoginVO.getVendorId());

		return "/edi/buy/NEDMBUY0112";
	}


}
