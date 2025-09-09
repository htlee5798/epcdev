package com.lottemart.epc.edi.comm.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;

@Controller
public class PEDPCOM0001Controller {
	
	@Autowired
	private PEDPCOM0001Service pedpcom0001service;
	
	@RequestMapping(value = "/edi/comm/PEDPCOM0001.do", method = RequestMethod.GET)
	public String periodStore(Locale locale, Model model) throws Exception {
		
		Map<String,Object> map = new HashMap();
		model.addAttribute("storeList",pedpcom0001service.selectStore(map));
		
		return "/edi/comm/PEDPCOM0001";
	}
	
}
