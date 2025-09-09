package com.lottemart.epc.edi.order.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.order.service.PEDMORD0020Service;


@Controller
public class PEDMORD0020Controller {

	@Autowired
	private PEDMORD0020Service pedmord0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//발주정보 납품가능정보 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0021.do", method = RequestMethod.GET)
	public String ordAble(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/order/PEDMORD0021";
	}
	
	//발주정보 납품가능정보 조회	
	@RequestMapping(value = "/edi/order/PEDMORD0021Select.do", method = RequestMethod.POST)
	public String selectOrdAble(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		model.addAttribute("orderList", pedmord0020Service.selectOrdAble(map));
		
		return "/edi/order/PEDMORD0021";
	}
	
	//발주정보 납품가능정보 변경	
	@RequestMapping(value = "/edi/order/PEDMORD0021Update.do", method = RequestMethod.POST)
	public String updateOrdSplyTime(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] tmphour = map.get("forward_hour").toString().split("-");
		String[] tmpmin = map.get("forward_min").toString().split("-");
		String[] tmpordno = map.get("forward_ordno").toString().split("-");
		String[] tmpprodno = map.get("forward_prodno").toString().split("-");
		
		String[] tmpTime = new String[tmphour.length];
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		for(int i=0;i<tmphour.length;i++){
			tmpTime[i] = tmphour[i]+tmpmin[i];
		}
		pedmord0020Service.updateOrdSplyTime(tmpTime, tmpordno, tmpprodno);
		
		return selectOrdAble(map, model, request);
		
	}
	
	//발주정보 신선매입정보변경 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0022.do", method = RequestMethod.GET)
	public String ordSply(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/order/PEDMORD0022";
	}
	
	//발주정보 신선매입정보변경 조회
	@RequestMapping(value = "/edi/order/PEDMORD0022Select.do", method = RequestMethod.POST)
	public String selectOrdSply(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		model.addAttribute("paramMap",map);
		model.addAttribute("orderList", pedmord0020Service.selectOrdSply(map));
		
		return "/edi/order/PEDMORD0022";
	}
	
	//발주정보 신선매입정보변경 수정
	@RequestMapping(value = "/edi/order/PEDMORD0022Update.do", method = RequestMethod.POST)
	public String updateOrdSply(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		pedmord0020Service.updateOrdSply(map);
		
		return selectOrdSply(map, model, request);
	}
	
	
}
