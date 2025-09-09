package com.lottemart.epc.edi.comm.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lottemart.epc.edi.comm.service.NEDPCOM0010Service;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;

@Controller
public class NEDPCOM0010Controller {
	
	@Autowired
	private NEDPCOM0010Service nedpcom0010service;
	
	@RequestMapping(value = "/edi/comm/NEDPCOM0010.do", method = RequestMethod.GET)
	public String periodStore(Locale locale, Model model) throws Exception {
		
		Map<String,Object> map = new HashMap();
		model.addAttribute("storeList",nedpcom0010service.selectStore(map));
		
		return "/edi/comm/NEDPCOM0010";
	}
	
}
