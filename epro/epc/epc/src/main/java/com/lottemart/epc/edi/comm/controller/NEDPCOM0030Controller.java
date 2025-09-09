package com.lottemart.epc.edi.comm.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;

@Controller
public class NEDPCOM0030Controller {

	@RequestMapping(value = "/edi/comm/NEDPCOM0030.do", method=RequestMethod.POST)
	public String excelExport(@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		return "/edi/comm/NEDPCOM0030";
	}

}
