package com.lottemart.epc.edi.inventory.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lcn.module.common.util.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 재고정보 - > 불량상품 조치  - > 불량상품 내역 Controller
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
public class NEDMINV0110Controller {


	/**
	 * 불량상품 조치  - > 불량상품 내역
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/inventory/NEDMINV0110.do")
	public String doInit(Locale locale, ModelMap model) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String tmp = "1";

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		map.put("measure", tmp);

		model.addAttribute("paramMap", map);

		return "/edi/inventory/NEDMINV0110";
	}


	/**
	 * 불량상품 조치  - > 불량상품 내역 popup 페이지
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edi/inventory/NEDMINV0111.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String selectBadProdPopup(@RequestParam Map<String,Object> map, ModelMap model) throws Exception {

		model.addAttribute("paramMap",map);

		return "/edi/inventory/NEDMINV0111";
	}
	
	/**
	 * 불량상품 조치  - > 불량상품 내역 popup 페이지 (TRU 상품 전용, 불량상품 페이지)
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edi/inventory/NEDMINV0112.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String selectBadTruProdPopup(@RequestParam Map<String,Object> map, ModelMap model) throws Exception {

		model.addAttribute("paramMap",map);

		return "/edi/inventory/NEDMINV0112";
	}
	
}
