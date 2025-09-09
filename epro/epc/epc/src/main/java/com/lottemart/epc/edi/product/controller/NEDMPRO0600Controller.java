package com.lottemart.epc.edi.product.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mngr")
public class NEDMPRO0600Controller {
	
	@RequestMapping(value="/edi/product/NEDMPRO0600.do")
	public String NEDMPRO0600Init(ModelMap model, HttpServletRequest requet) throws Exception {
		return "edi/product/NEDMPRO0600";
	}

}
