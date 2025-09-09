package com.lottemart.epc.common.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0006Service;

@Controller
public class PSCMCOM0010Controller {
	
	@Autowired
	private ConfigurationService config;
	

	@Autowired
	private PSCMCOM0006Service pscmcom0006Service;	
	
	@RequestMapping(value = "/common/PSCMCOM0010.do", method = RequestMethod.GET)
	public String periodStore(Locale locale, Model model) throws Exception {	
		return "common/PSCMCOM0010";
	}
	
	@RequestMapping(value = "/common/PSCMCOM0010Search.do", method = RequestMethod.POST)
	public String periodProductSearch(@RequestParam Map<String,Object> map, Model model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("productList",pscmcom0006Service.selectProduct(map));
		
		return "common/PSCMCOM0010";
	}	
}
