package com.lottemart.epc.edi.consult.controller;

import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class PEDMSCT0008Controller {

	
	 //거래절차
	@RequestMapping(value = "/edi/consult/PEDMCST0008.do", method = RequestMethod.GET)
	public String tranProcess(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST000801";
	}
	
	 //거래절차
	@RequestMapping(value = "/edi/consult/PEDMCST000802.do", method = RequestMethod.GET)
	public String tranProcess2(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST000802";
	}
	
	 //인테리어비용 보상 기준
	@RequestMapping(value = "/edi/consult/PEDMCST000803.do", method = RequestMethod.GET)
	public String tranProcess3(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST000803";
	}
	
	
    
}
