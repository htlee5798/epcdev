package com.lottemart.epc.edi.comm.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.PEDPCOM0002Service;

@Controller
public class PEDPCOM0002Controller {
	
	@Autowired
	private PEDPCOM0002Service pedpcom0002service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	
	@RequestMapping(value = "/edi/comm/PEDPCOM0002.do", method = RequestMethod.GET)
	public String periodProduct(Locale locale, Model model) throws Exception {
		
		return "/edi/comm/PEDPCOM0002";
	}
	
	@RequestMapping(value = "/edi/comm/PEDPCOM0002Search.do", method = RequestMethod.POST)
	public String periodProductSearch(@RequestParam Map<String,Object> map, Model model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("productList",pedpcom0002service.selectProduct(map));
		
		return "/edi/comm/PEDPCOM0002";
	}
	
	@RequestMapping(value = "/edi/comm/PEDPCOM0002Estimate.do", method = RequestMethod.GET)
	public String periodProductEstimate(HttpServletRequest request,Locale locale, Model model) throws Exception {
		
		String tmp = request.getParameter("product_seq");
		
		model.addAttribute("value",tmp);
		
		return "/edi/consult/estimatePopup";
	}
	
	@RequestMapping(value = "/edi/comm/PEDPCOM0002EstimateSearch.do", method = RequestMethod.POST)
	public String periodProductSearchEstimate(@RequestParam Map<String,Object> map, Model model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("pro_sele_val",map);
		model.addAttribute("productList",pedpcom0002service.selectProduct(map));
		
		return "/edi/consult/estimatePopup";
	}
	
}

