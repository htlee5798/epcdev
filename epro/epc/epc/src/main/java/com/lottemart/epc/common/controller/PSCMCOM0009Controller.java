package com.lottemart.epc.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.service.PSCMDLV0006Service;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;

@Controller
public class PSCMCOM0009Controller {
	
	@Autowired
	private PSCMDLV0006Service pscmdlv0006Service;
	
	@RequestMapping(value = "/common/PSCMCOM0009.do", method = RequestMethod.GET)
	public String periodStore(Locale locale, Model model) throws Exception {
		
		
		List<DataMap> strCdList = pscmdlv0006Service.selectAllOnlineStore();
		model.addAttribute("storeList", strCdList);		
		
		return "common/PSCMCOM0009";
	}
}
