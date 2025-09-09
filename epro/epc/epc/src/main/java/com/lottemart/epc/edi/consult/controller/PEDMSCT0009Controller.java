package com.lottemart.epc.edi.consult.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.service.PEDMSCT0009Service;
import com.lottemart.epc.edi.comm.model.SearchParam;




@Controller
public class PEDMSCT0009Controller {
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private PEDMSCT0009Service pedmsct0009Service;

	
	//발주중단 첫페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0009.do", method = RequestMethod.GET)
	public String orderStop(Locale locale,  ModelMap model) {
		
		return "/edi/consult/PEDMCST0009";
	}
	
	//발주중단 조회
	@RequestMapping(value = "/edi/consult/PEDMCST0009Select.do", method = RequestMethod.POST)
	public String orderStopSelect(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		String[] pros = map.get("productVal").toString().split(";");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		if(!"".equals(pros[0])){
			map.put("productVal", pros);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		model.addAttribute("conList", pedmsct0009Service.orderStopSelect(map));
		
		
		return "/edi/consult/PEDMCST0009";
	}
	
	//발주중단 등록
	@RequestMapping(value = "/edi/consult/PEDMCST0009Insert.do", method = RequestMethod.POST)
	public String orderStopInsert(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String receive = map.get("forward_value").toString();
		
		pedmsct0009Service.orderStopInsert(receive);
		
		return "/edi/consult/PEDMCST0009Result";
	}
	
	
	
    
}
