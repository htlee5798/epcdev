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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.buy.model.NEDMBUY0080VO;
import com.lottemart.epc.edi.comm.model.RFCCommonVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.commonUtils.commonUtil;


/**
 * 매입정보 - > 기간별 매입정보  - > 증정품 확정 Controller
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
public class NEDMBUY0080Controller {

	/*@Autowired
	private NEDMBUY0080Service NEDMBUY0080Service;*/
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	
	/**
	 * 매입정보 - > 기간별 매입정보  - > 증정품 확정 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0080.do", method = RequestMethod.GET)
	public String gift(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/buy/NEDMBUY0080";
	}
	
	
	/**
	 * 증정품 확정 Txt파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0080Text.do", method=RequestMethod.POST)
	public void NEDMDLY0120Text(NEDMBUY0080VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		sb.append("■ 매입정보(증점품확정)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("센터명;점포명;증정바코드;증정상품명;입수;발주단위;입하예정수량;확정수량;납품유형;물류루트");
		sb.append("\r\n");
		
		// 성공이 아니면 실패로 간주한다.
		if (Integer.parseInt(rtnCnt) == 1) {
			JSONObject jObj = resultObj.getJSONObject("TAB");
			
			sb.append(jObj.getString("CEN_NM"));
			sb.append(";");
			sb.append(jObj.getString("COMPANY"));
			sb.append(";");
			sb.append(jObj.getString("RETAILSKU"));
			sb.append(";");
			sb.append(jObj.getString("DESCR"));
			sb.append(";");
			sb.append(jObj.getString("PACKKEY"));
			sb.append(";");
			sb.append(jObj.getString("UOM"));
			
			sb.append(";");
			sb.append(jObj.getString("ORD_QTY"));
			sb.append(";");
			sb.append(jObj.getString("BUY_QTY"));
			sb.append(";");
			sb.append(jObj.getString("TEMP"));
			sb.append(";");
			sb.append(jObj.getString("ROUTE"));
			sb.append(";");			
			sb.append("\r\n");
		} else if (Integer.parseInt(rtnCnt) > 1) {
			JSONArray arr = resultObj.getJSONArray("TAB");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jObj = arr.getJSONObject(i);
				//for (int j = 0; j < jObj.names().length(); j++) {
					//System.out.println("key = " + jObj.names().getString(j) + " value = " + jObj.get(jObj.names().getString(j)));
					     					  			
					sb.append(jObj.getString("CEN_NM"));
					sb.append(";");
					sb.append(jObj.getString("COMPANY"));
					sb.append(";");
					sb.append(jObj.getString("RETAILSKU"));
					sb.append(";");
					sb.append(jObj.getString("DESCR"));
					sb.append(";");
					sb.append(jObj.getString("PACKKEY"));
					sb.append(";");
					sb.append(jObj.getString("UOM"));
					
					sb.append(";");
					sb.append(jObj.getString("ORD_QTY"));
					sb.append(";");
					sb.append(jObj.getString("BUY_QTY"));
					sb.append(";");
					sb.append(jObj.getString("TEMP"));
					sb.append(";");
					sb.append(jObj.getString("ROUTE"));
					sb.append(";");					
					sb.append("\r\n");
				//}
				//System.out.println(o);  
			}
		}
		
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
}
