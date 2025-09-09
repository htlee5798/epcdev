package com.lottemart.epc.edi.ems.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lcn.module.common.util.DateUtil;

import com.lottemart.epc.edi.ems.service.PEDMEMS0000Service;


@Controller
public class PEDMEMS0000Controller {

	@Autowired
	private PEDMEMS0000Service pedmems0000Service;
	
	
	@RequestMapping(value = "/edi/ems/PEDMEMS0001.do", method = RequestMethod.GET)
	public String selectEmsData(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		model.addAttribute("ems", pedmems0000Service.selectEmsData(map));
		
		return "/edi/ems/PEDMEMS0001";
	}
	
}
