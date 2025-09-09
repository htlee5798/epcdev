package com.lottemart.epc.edi.sample.controller;


import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.edi.sample.service.PEDMSAM001Service;


@Controller
public class PEDMSAM001Controller {
	
	@Autowired
	private PEDMSAM001Service pedmsam001Service;
	
	@RequestMapping(value = "/edi/sample/PEDMSAM001.do", method = RequestMethod.GET)
	public String sample01(Locale locale, Model model) {
		return "/edi/sample/PEDMSAM001";
	}
	
	@RequestMapping(value = "/edi/sample/PEDMSAM001Search.do", method = RequestMethod.POST)
	public String sample01Search(@RequestParam Map<String,Object> map, ModelMap model) throws Exception {
		
		model.addAttribute("param",map);
		model.addAttribute("orderList", pedmsam001Service.selectOrderInfo(map));
		
		return "/edi/sample/PEDMSAM001";
	}
	
	
	
	
}
