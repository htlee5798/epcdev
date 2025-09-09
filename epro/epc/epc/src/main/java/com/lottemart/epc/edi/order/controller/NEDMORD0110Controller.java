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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.order.model.NEDMORD0110VO;
import com.lottemart.epc.edi.order.model.NEDMORD0120VO;
import com.lottemart.epc.edi.order.service.NEDMORD0010Service;
import com.lottemart.epc.edi.order.service.NEDMORD0110Service;
import com.lottemart.epc.edi.order.service.PEDMORD0020Service;

/**
 * 발주정보 - > 주문응답서 - > 납품가능정보 Controller
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
public class NEDMORD0110Controller {

	@Autowired
	private NEDMORD0110Service nedmord0110Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 발주정보(납품가능정보) 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0110.do", method = RequestMethod.GET)
	public String ordAble(ModelMap model, HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		//Defalut 날짜 입력(현재날짜)
		map.put("srchStartDate", nowDate);
		map.put("srchEndDate", nowDate);
		
		model.addAttribute("paramMap",map);
		String returnPage = "/edi/order/NEDMORD0110";
		return returnPage;
	}
	
	/**
	 * 발주정보 납품가능정보 조회	
	 * @param NEDMORD0110VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0110Select.json", method = RequestMethod.POST)
	public String selectOrdAble(ModelMap model, @RequestBody NEDMORD0110VO map, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		
		model.addAttribute("orderList", nedmord0110Service.selectOrdAble(map));
		
		return "/edi/order/NEDMORD0111AJAX";
	}
	
	/**
	 * 발주정보 납품가능정보 변경	
	 * @param NEDMORD0110VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0110Update.json", method = RequestMethod.POST)
	public @ResponseBody NEDMORD0110VO updateOrdSplyTime(ModelMap model, @RequestBody NEDMORD0110VO map, HttpServletRequest request) throws Exception {
		nedmord0110Service.updateOrdSplyTime(map);
		return map;
	}
	
}
