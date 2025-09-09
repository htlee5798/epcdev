package com.lottemart.epc.edi.buy.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.buy.service.PEDMBUY0020Service;


@Controller
public class PEDMBUY0020Controller {

	@Autowired
	private PEDMBUY0020Service pedmbuy0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@RequestMapping(value = "/edi/buy/PEDMBUY0021Baner.do", method = RequestMethod.GET)
	public String outSideUrl(@RequestParam Map<String,Object> map, Locale locale,  ModelMap model) {
		
		map.put("defPgmID", "/edi/buy/PEDMBUY0021.do");
		map.put("pgm_code", "BUY");
		map.put("pgm_sub",  "2");
		model.addAttribute("paramMap",map);
		
		
		
		return "/edi/main/ediIndex";
	}
	
	
	@RequestMapping(value = "/edi/buy/PEDMBUY0021.do", method = RequestMethod.GET)
	public String reject(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String tmp = "1";
		
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		map.put("measure", tmp);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/buy/PEDMBUY0021";
	}
	
	@RequestMapping(value = "/edi/buy/PEDMBUY0021Select.do", method = RequestMethod.POST)
	public String selectReject(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		model.addAttribute("buyList", pedmbuy0020Service.selectRejectInfo(map));
		
		return "/edi/buy/PEDMBUY0021";
	}
	
	@RequestMapping(value = "/edi/buy/PEDPBUY0021.do", method = RequestMethod.POST)
	public String rejectPopup(@RequestParam Map<String,Object> map, ModelMap model) throws Exception {
		
		model.addAttribute("paramMap",map);
		model.addAttribute("buyList",(HashMap)pedmbuy0020Service.selectRejectPopupInfo(map));
		
		return "/edi/buy/PEDPBUY0021";
	}
	
	@RequestMapping(value = "/edi/buy/PEDPBUY0021UPDATE.do", method = RequestMethod.POST)
	public String rejectPopupUpdate(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		
		pedmbuy0020Service.updateRejectPopup(map);
		
		rejectPopup(map, model);
		
		return "/edi/buy/PEDPBUY0021";
	}
}
