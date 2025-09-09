package com.lottemart.epc.edi.delivery.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.delivery.service.PEDMDLY0020Service;


@Controller
public class PEDMDLY0020Controller {

	@Autowired
	private PEDMDLY0020Service pedmdly0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//현황표
	@RequestMapping(value = "/edi/delivery/PEDMDLY0021.do", method = RequestMethod.GET)
	public String status(Locale locale,  ModelMap model,HttpServletRequest request) {
		
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("startDate", commonUtil.firstDate(nowDate));
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/delivery/PEDMDLY0021";
	}
	
	//현황표
	@RequestMapping(value = "/edi/delivery/PEDMDLY0021Select.do", method = RequestMethod.POST)
	public String selectStatus(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		model.addAttribute("deliveryList", pedmdly0020Service.selectStatusInfo(map));
		
		return "/edi/delivery/PEDMDLY0021";
	}
	
	//접수확인
	@RequestMapping(value = "/edi/delivery/PEDMDLY0022.do", method = RequestMethod.GET)
	public String deliverAccept(Locale locale,  ModelMap model,HttpServletRequest request) {
		
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/delivery/PEDMDLY0022";
	}
	
	//접수확인
	@RequestMapping(value = "/edi/delivery/PEDMDLY0022Select.do", method = RequestMethod.POST)
	public String selectDeliverAccept(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		model.addAttribute("deliveryList", pedmdly0020Service.selectDeliverAcceptInfo(map));
		
		return "/edi/delivery/PEDMDLY0022";
	}
	
	//접수확인 txt파일 생성
	@RequestMapping(value = "/edi/delivery/PEDMDLY0022Text.do", method = RequestMethod.POST)
	public void createTextDeliverAccept(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		pedmdly0020Service.createTextDeliverAccept(map,request,response);
		
	}
	
	//접수확인 수정
	@RequestMapping(value = "/edi/delivery/PEDMDLY0022Update.do", method = RequestMethod.POST)
	public String updateDeliverAccept(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String tmp = map.get("forwardValue").toString();
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		pedmdly0020Service.updateDeliverAcceptInfo(tmp);
		
		selectDeliverAccept(map, model, request);
		
		return "/edi/delivery/PEDMDLY0022";
	}
	
	//완료등록
	@RequestMapping(value = "/edi/delivery/PEDMDLY0023.do", method = RequestMethod.GET)
	public String deliverReg(Locale locale,  ModelMap model,HttpServletRequest request) {
		
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/delivery/PEDMDLY0023";
	}
	
	//완료등록
	@RequestMapping(value = "/edi/delivery/PEDMDLY0023Select.do", method = RequestMethod.POST)
	public String selectDeliverReg(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		
		model.addAttribute("paramMap",map);
		model.addAttribute("deliveryList", pedmdly0020Service.selectDeliverRegInfo(map));
		
		return "/edi/delivery/PEDMDLY0023";
	}
	
	//완료등록 txt파일 생성
	@RequestMapping(value = "/edi/delivery/PEDMDLY0023Text.do", method = RequestMethod.POST)
	public void createTextDeliverReg(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		pedmdly0020Service.createTextDeliverReg(map,request,response);
		
	}
	
	//완료등록
	@RequestMapping(value = "/edi/delivery/PEDMDLY0023UpdateSelect.do", method = RequestMethod.POST)
	public String updateSelectDeliverReg(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(stors.length>1){
			map.put("storeVal", stors);
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		
		model.addAttribute("paramMap",map);
		model.addAttribute("deliveryList", pedmdly0020Service.selectDeliverRegInfo(map));
		
		return "/edi/delivery/PEDMDLY002301";
	}
	
	//완료등록 수정
	@RequestMapping(value = "/edi/delivery/PEDMDLY0023Update.do", method = RequestMethod.POST)
	public String updateDeliverReg(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String tmp = map.get("forwardValue").toString();
		String tmp2 = map.get("forwardValue2").toString();
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		pedmdly0020Service.updateDeliverRegInfo(tmp, tmp2);
		
		selectDeliverReg(map, model, request);
		
		return "/edi/delivery/PEDMDLY0023";
	}
	
	

	
	
}
