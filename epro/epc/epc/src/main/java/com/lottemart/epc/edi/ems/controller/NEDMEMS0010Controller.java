package com.lottemart.epc.edi.ems.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.edi.ems.service.NEDMEMS0010Service;

@Controller
public class NEDMEMS0010Controller {
	
	@Autowired
	private NEDMEMS0010Service nedmems0010Service;
	
	@RequestMapping(value = "/edi/ems/NEDMEMS0010.do", method = RequestMethod.GET)
	public String selectEmsData(@RequestParam Map<String,Object> map, ModelMap model, HttpServletRequest request) throws Exception {
		model.addAttribute("ems", nedmems0010Service.selectEmsData(map));
		
		return "/edi/ems/NEDMEMS0010";
	}
	
}
