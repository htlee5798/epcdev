package com.lottemart.epc.edi.consult.controller;

import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class PEDMSCT0007Controller {

	
    //Loan
	@RequestMapping(value = "/edi/consult/PEDMCST0007.do", method = RequestMethod.GET)
	public String loanMain(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST0007";
	}
	
	//Loan
	@RequestMapping(value = "/edi/consult/PEDMCST000701.do", method = RequestMethod.GET)
	public String loanDetail1(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST000701";
	}
	
	//Loan
	@RequestMapping(value = "/edi/consult/PEDMCST000702.do", method = RequestMethod.GET)
	public String loanDetail2(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST000702";
	}
	
    
}
