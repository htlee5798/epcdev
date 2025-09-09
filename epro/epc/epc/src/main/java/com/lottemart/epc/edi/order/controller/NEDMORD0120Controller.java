package com.lottemart.epc.edi.order.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.lottemart.epc.edi.order.model.NEDMORD0120VO;
import com.lottemart.epc.edi.order.service.NEDMORD0120Service;

/**
 * 발주정보 - > 주문응답서 - > 신선매입정보변경Controller
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
public class NEDMORD0120Controller {

	@Autowired
	private NEDMORD0120Service nedmord0120Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 발주정보(신선매입정보변경) 첫페이지
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0120.do", method = RequestMethod.GET)
	public String ordAble(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		//Defalut 날짜 입력(현재날짜)
		map.put("srchStartDate", nowDate);
		map.put("srchEndDate", nowDate);
		
		model.addAttribute("paramMap",map);
		String returnPage = "/edi/order/NEDMORD0120";
		return returnPage;
	}
	
	/**
	 * 발주정보(신선매입정보변경) 첫페이지
	 * @param NEDMORD0120VO
	 * @param HttpServletRequest
	 * 
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0120Select.json", method = RequestMethod.POST)
	public String selectOrdSply(ModelMap model,@RequestBody NEDMORD0120VO map, HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		
		model.addAttribute("orderList", nedmord0120Service.selectOrdSply(map));
		
		return "/edi/order/NEDMORD0121AJAX";
	}
	
	/**
	 * 발주정보(발주정보 신선매입정보변경 수정) 
	 * @param NEDMORD0120VO
	 * @param HttpServletRequest
	 * 
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0120Update.json", method = RequestMethod.POST)
	public @ResponseBody NEDMORD0120VO updateOrdSply(ModelMap model,@RequestBody NEDMORD0120VO map, HttpServletRequest request) throws Exception {
		nedmord0120Service.updateOrdSply(map);
		return map;
	}
	
	
	
	
	
}
