package com.lottemart.epc.edi.delivery.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.RFCCommonVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.delivery.model.NEDMDLY0120VO;

/**
 * 배달정보 - > 토이배달  - > 접수확인 Controller
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
public class NEDMDLY0120Controller {
	private static final Logger logger = LoggerFactory.getLogger(NEDMDLY0120Controller.class);

	@Autowired
	private RFCCommonService rfcCommonService;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 토이배달  - > 접수확인
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/delivery/NEDMDLY0120.do", method = RequestMethod.GET)
	public String deliverAccept(Locale locale,  ModelMap model,HttpServletRequest request) {

		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		logger.debug("admiId" + epcLoginVO.getAdminId());
		logger.debug("RepVendorId" + epcLoginVO.getRepVendorId());
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);

		model.addAttribute("paramMap",map);

		return "/edi/delivery/NEDMDLY0120";
	}


	@RequestMapping(value = "/edi/delivery/NEDMDLY0120ServerDate.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> nowDate(HttpServletRequest request) throws Exception {
		String nowDate = DateUtil.getToday("yyyyMMdd");
		Map<String, Object> resultMap = new HashMap();
		resultMap.put("nowDate", nowDate);

		return resultMap;
	}

	/**
	 * 토이배달  - > 접수확인(텍스트파일)
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/delivery/NEDMDLY0120Text.do", method=RequestMethod.POST)
	public void NEDMDLY0120Text(NEDMDLY0120VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

		StringBuffer sb = new StringBuffer();

		sb.append("■ 배달 접수확인");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("전표번호;점포명;판매코드;상품명;희망배송일;수량;판매일자;의뢰고객명;의뢰주소;의뢰전화1;의뢰전화2;접수일자;인수고객;인수주소;인수전화1;인수전화2;접수여부");
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

			if (jObj.getString("ACCEPT_FG").trim().equals("0")) {
				sb.append("미접수");
			} else {
				sb.append("접수");
			}

			sb.append("\r\n");
		} else if (Integer.parseInt(rtnCnt) > 1) {
			JSONArray arr = resultObj.getJSONArray("TAB");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jObj = arr.getJSONObject(i);
				//for (int j = 0; j < jObj.names().length(); j++) {
					//logger.debug("key = " + jObj.names().getString(j) + " value = " + jObj.get(jObj.names().getString(j)));

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

					if (jObj.getString("ACCEPT_FG").trim().equals("0")) {
						sb.append("미접수");
					} else {
						sb.append("접수");
					}

					sb.append("\r\n");
				//}
				//logger.debug(o);
			}
		}

		sb.append("------------------------------------------------------------------------------------");

		commonUtil.createTextFile(request, response, sb);
	}

	@RequestMapping(value = "/edi/delivery/NEDMDLY0121.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String viewDeliverAccept(HttpServletRequest request, NEDMDLY0120VO vo, ModelMap model) throws Exception {

		List<String> hangmokList = Arrays.asList(vo.getFormHangmok());
		List<String> entpCdList  = Arrays.asList(vo.getFormEntpCd());
		List<String> strCdList   = Arrays.asList(vo.getFormStrCd());

		model.addAttribute("hangmokList",hangmokList);
		model.addAttribute("entpCdList" ,entpCdList);
		model.addAttribute("strCdList"  ,strCdList);

		model.addAttribute("dlyProdInfo",vo);
		return "/edi/delivery/NEDMDLY0121";
	}

	/**
	 * RFC Call
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/comm/rfcCallDlyProdMasking.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> rfcCallDlyProdMasking(@RequestBody NEDMDLY0120VO vo , HttpServletRequest request, HttpServletResponse response) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		Map<String, Object>	result = new HashMap<String, Object>();

		RFCCommonVO paramVO = new RFCCommonVO();
		paramVO.setProxyNm(vo.getProxyNm());
		paramVO.setParam(vo.getParam());
		result =  rfcCommonService.rfcCall(paramVO.getProxyNm(), paramVO.getParam(),epcLoginVO.getAdminId());

		boolean isRows = false;

		org.json.simple.JSONObject selectedObject = (org.json.simple.JSONObject)result.get("result");
		if(selectedObject.containsKey("TAB")) {

			if(!(selectedObject.get("TAB") instanceof org.json.simple.JSONObject))isRows=true;
			org.json.simple.JSONObject selectedDlyProd = new org.json.simple.JSONObject();
			org.json.simple.JSONArray dlyProdList = new org.json.simple.JSONArray();

			int rowsLen = 1;
			if(isRows) {
				dlyProdList = (org.json.simple.JSONArray)selectedObject.get("TAB");
				rowsLen = dlyProdList.size();
			}

			for(int idx=0; idx<rowsLen;idx++){
				if(isRows){
					selectedDlyProd = (org.json.simple.JSONObject)dlyProdList.get(idx);
				}
				else {
					selectedDlyProd = (org.json.simple.JSONObject)selectedObject.get("TAB");
				}

				if(selectedDlyProd.containsKey("RECV_NM") && !selectedDlyProd.get("RECV_NM").equals("")){
						String recvNm = (String)selectedDlyProd.get("RECV_NM");
						selectedDlyProd.put("RECV_NM",recvNm.replaceAll("([가-힣])([가-힣])([가-힣]*)","$1*$3"));
				}
				if(selectedDlyProd.containsKey("CUST_NM") && !selectedDlyProd.get("CUST_NM").equals("")){
						String custNm = (String)selectedDlyProd.get("CUST_NM");
						selectedDlyProd.put("CUST_NM",custNm.replaceAll("([가-힣])([가-힣])([가-힣]*)","$1*$3"));
				}
				if(selectedDlyProd.containsKey("CUST_TEL_NO1") && !selectedDlyProd.get("CUST_TEL_NO1").equals("")){
						String custTelNo1 = (String)selectedDlyProd.get("CUST_TEL_NO1");
						selectedDlyProd.put("CUST_TEL_NO1",custTelNo1.replaceAll("^(\\d{2,3})[-](\\d{3,4})[-](\\d{4})","$1-****-$3"));
				}
				if(selectedDlyProd.containsKey("CUST_TEL_NO2") && !selectedDlyProd.get("CUST_TEL_NO2").equals("")){
						String custTelNo2 = (String)selectedDlyProd.get("CUST_TEL_NO2");
						selectedDlyProd.put("CUST_TEL_NO2",custTelNo2.replaceAll("^(\\d{2,3})[-](\\d{3,4})[-](\\d{4})","$1-****-$3"));
				}
				if(selectedDlyProd.containsKey("RECV_TEL_NO1") && !selectedDlyProd.get("RECV_TEL_NO1").equals("")){
						String recvTelNo1 = (String)selectedDlyProd.get("RECV_TEL_NO1");
						selectedDlyProd.put("RECV_TEL_NO1",recvTelNo1.replaceAll("^(\\d{2,3})[-](\\d{3,4})[-](\\d{4})","$1-****-$3"));
				}
				if(selectedDlyProd.containsKey("RECV_TEL_NO2") && !selectedDlyProd.get("RECV_TEL_NO2").equals("")){
						String recvTelNo2 = (String)selectedDlyProd.get("RECV_TEL_NO2");
						selectedDlyProd.put("RECV_TEL_NO2",recvTelNo2.replaceAll("^(\\d{2,3})[-](\\d{3,4})[-](\\d{4})","$1-****-$3"));
				}
				if(selectedDlyProd.containsKey("CUST_ADDR") && !selectedDlyProd.get("CUST_ADDR").equals("")){
						String custAddr = (String)selectedDlyProd.get("CUST_ADDR");
						selectedDlyProd.put("CUST_ADDR",custAddr.replaceAll(".*","**********"));
				}
				if(selectedDlyProd.containsKey("RECV_ADDR") && !selectedDlyProd.get("RECV_ADDR").equals("")){
						String recvAddr = (String)selectedDlyProd.get("RECV_ADDR");
						selectedDlyProd.put("RECV_ADDR",recvAddr.replaceAll(".*","**********"));
				}
			}
		}
		return result;
	}

	/**
	 * RFC Call
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/comm/rfcCallDlyProd.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> rfcCallDlyProd(@RequestBody NEDMDLY0120VO vo , HttpServletRequest request, HttpServletResponse response) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		Map<String, Object>	result = new HashMap<String, Object>();
		boolean isSuccess = false;
		RFCCommonVO paramVO = new RFCCommonVO();
		paramVO.setProxyNm(vo.getProxyNm());
		paramVO.setParam(vo.getParam());

		result =  rfcCommonService.rfcCall(paramVO.getProxyNm(), paramVO.getParam(),epcLoginVO.getAdminId());

		org.json.simple.JSONObject selectedObject = (org.json.simple.JSONObject)result.get("result");
		if(selectedObject.containsKey("TAB")) {
			if(!(selectedObject.get("TAB") instanceof org.json.simple.JSONObject))
			{
				org.json.simple.JSONArray dlyProdList = (org.json.simple.JSONArray)selectedObject.get("TAB");

				// [210128 전표번호 조회] S
				for( int idx=0; idx<dlyProdList.size();idx++){
					org.json.simple.JSONObject selectedDlyProd = (org.json.simple.JSONObject)dlyProdList.get(idx);
					if(selectedDlyProd.get("SLIP_NO").equals(vo.getSlipNo()) &&
							selectedDlyProd.get("SRCMK_CD").equals(vo.getSrcmkCd())){
						selectedObject.put("TAB", selectedDlyProd);
						isSuccess = true;
						break;
					}
				}

				// [210128 전표번호 조회] E
				if(isSuccess)System.out.println("[rfcCallDlyProd.json]성공");
				else System.out.println("[rfcCallDlyProd.json].실패");
				result.put("result",selectedObject);
			}
		}
		return result;
	}
}
