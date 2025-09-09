package com.lottemart.epc.edi.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0110VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0110Service;

/**
 * 결산정보 - > 거래실적조회  - > 거래실적조회 Controller
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
public class NEDMPAY0110Controller {

	@Autowired
	private NEDMPAY0110Service nedmpay0110Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 거래실적조회  - > 거래실적조회
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0110.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String credLed(Locale locale,  ModelMap model,HttpServletRequest request) {
		String srchEndDate = request.getParameter("srchEndDate");
		
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		
		if(srchEndDate != null && !"".equals(srchEndDate)){
			nowDateYear = srchEndDate.substring(0, 4);
			nowDateMonth = srchEndDate.substring(4, 6);
			map.put("startDate_year",  nowDateYear);
			map.put("startDate_month", nowDateMonth);
			map.put("endDate_year",    nowDateYear);
			map.put("endDate_month",   nowDateMonth);
			map.put("srchStartDate", srchEndDate);
			map.put("srchEndDate", srchEndDate);
		}else{
			map.put("startDate_year",  nowDateYear);
			map.put("startDate_month", nowDateMonth);
			map.put("endDate_year",    nowDateYear);
			map.put("endDate_month",   nowDateMonth);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/NEDMPAY0110";
	}
	
	/**
	 * 거래실적조회  - > 거래실적조회
	 * @param NEDMPAY0110VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0110Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectCredLed(@RequestBody NEDMPAY0110VO map,HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMPAY0110VO> paymentList = nedmpay0110Service.selectCredLedInfo(map);

		resultMap.put("paymentList", paymentList);
		return resultMap;
		
	}
	
	/**
	 * 거래실적조회  - > 거래실적조회  txt파일 생성
	 * @param NEDMPAY0110VO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0110Text.do", method = RequestMethod.POST)
	public void createTextCredLed(NEDMPAY0110VO map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
			
		
		nedmpay0110Service.createTextCredLed(map,request,response);
	}
	
	
}
