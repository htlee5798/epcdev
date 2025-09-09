package com.lottemart.epc.edi.inventory.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.inventory.model.PEDMINV0020VO;
import com.lottemart.epc.edi.inventory.service.PEDMINV0020Service;


@Controller
public class PEDMINV0020Controller {

	@Autowired
	private PEDMINV0020Service pedminv0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	
	
	@RequestMapping(value = "/edi/inventory/PEDMINV0021Baner.do", method = RequestMethod.GET)
	public String outSideUrl(@RequestParam Map<String,Object> map, Locale locale,  ModelMap model) {
		
		map.put("defPgmID", "/edi/inventory/PEDMINV0021.do");
		map.put("pgm_code", "INV");
		map.put("pgm_sub",  "2");
		model.addAttribute("paramMap",map);
		
		return "/edi/main/ediIndex";
	}
	
	@RequestMapping(value = "/edi/inventory/PEDMINV0021.do", method = RequestMethod.GET)
	public String badProd(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String tmp = "1";
		
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		map.put("measure", tmp);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/inventory/PEDMINV0021";
	}
	
	@RequestMapping(value = "/edi/inventory/PEDMINV0021Select.do", method = RequestMethod.POST)
	public String selectBadProd(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		model.addAttribute("inventoryList", pedminv0020Service.selectBadProdInfo(map));
		
		return "/edi/inventory/PEDMINV0021";
	}
	
	@RequestMapping(value = "/edi/inventory/PEDPINV0021.do", method = RequestMethod.POST)
	public String selectBadProdPopup(@RequestParam Map<String,Object> map, ModelMap model) throws Exception {
		
		model.addAttribute("paramMap",map);
		model.addAttribute("inventoryList", (PEDMINV0020VO)pedminv0020Service.selectBadProdPopupInfo(map));
		
		return "/edi/inventory/PEDPINV0021";
	}
	
	@RequestMapping(value = "/edi/inventory/PEDPINV0021UPDATE.do", method = RequestMethod.POST)
	public String selectBadProdPopupUpdate(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		
		pedminv0020Service.selectBadProdPopupUpdate(map);
		
		selectBadProdPopup(map, model);
		
		return "/edi/inventory/PEDPINV0021";
	}
	
	
	
}
