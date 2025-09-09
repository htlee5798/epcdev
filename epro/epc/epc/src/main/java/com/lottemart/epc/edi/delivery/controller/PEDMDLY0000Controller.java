package com.lottemart.epc.edi.delivery.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.delivery.service.PEDMDLY0000Service;


@Controller
public class PEDMDLY0000Controller {

	@Autowired
	private PEDMDLY0000Service pedmdly0000Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//현황표
	@RequestMapping(value = "/edi/delivery/PEDMDLY0001.do", method = RequestMethod.GET)
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
		
		return "/edi/delivery/PEDMDLY0001";
	}
	
	//현황표
	@RequestMapping(value = "/edi/delivery/PEDMDLY0001Select.do", method = RequestMethod.POST)
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
		model.addAttribute("deliveryList", pedmdly0000Service.selectStatusInfo(map));
		
		return "/edi/delivery/PEDMDLY0001";
	}
	
	//접수확인
	@RequestMapping(value = "/edi/delivery/PEDMDLY0002.do", method = RequestMethod.GET)
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
		
		return "/edi/delivery/PEDMDLY0002";
	}
	
	//접수확인
	@RequestMapping(value = "/edi/delivery/PEDMDLY0002Select.do", method = RequestMethod.POST)
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
		model.addAttribute("deliveryList", pedmdly0000Service.selectDeliverAcceptInfo(map));
		
		return "/edi/delivery/PEDMDLY0002";
	}
	
	//접수확인 txt 파일생성
	@RequestMapping(value = "/edi/delivery/PEDMDLY0002Text.do", method = RequestMethod.POST)
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
		
		pedmdly0000Service.createTextDeliverAccept(map,request,response);
		
	}
	
	//접수확인 수정
	@RequestMapping(value = "/edi/delivery/PEDMDLY0002Update.do", method = RequestMethod.POST)
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
		
		pedmdly0000Service.updateDeliverAcceptInfo(tmp);
		
		selectDeliverAccept(map, model, request);
		
		return "/edi/delivery/PEDMDLY0002";
	}
	
	//취소전표  조회
	@RequestMapping(value = "/edi/delivery/PEDPDLY0002Select.do", method = RequestMethod.POST)
	public String cancelDeliverAccept(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		model.addAttribute("deliveryList", pedmdly0000Service.cancelDeliverAccept(map));
		
		return "/edi/delivery/PEDPDLY0002";
	}
	
	//완료등록
	@RequestMapping(value = "/edi/delivery/PEDMDLY0003.do", method = RequestMethod.GET)
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
		
		return "/edi/delivery/PEDMDLY0003";
	}
	
	//완료등록
	@RequestMapping(value = "/edi/delivery/PEDMDLY0003Select.do", method = RequestMethod.POST)
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
		model.addAttribute("deliveryList", pedmdly0000Service.selectDeliverRegInfo(map));
		
		return "/edi/delivery/PEDMDLY0003";
	}
	
	//완료등록 txt 파일생성
	@RequestMapping(value = "/edi/delivery/PEDMDLY0003Text.do", method = RequestMethod.POST)
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
		
		pedmdly0000Service.createTextDeliverReg(map,request,response);
		
	}
	
	//완료등록
	@RequestMapping(value = "/edi/delivery/PEDMDLY0003UpdateSelect.do", method = RequestMethod.POST)
	public String updateSelectDeliverReg(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("deliveryList", pedmdly0000Service.selectDeliverRegInfo(map));
		
		return "/edi/delivery/PEDMDLY000301";
	}
	
	//완료등록 수정
	@RequestMapping(value = "/edi/delivery/PEDMDLY0003Update.do", method = RequestMethod.POST)
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
		
		pedmdly0000Service.updateDeliverRegInfo(tmp, tmp2);
		
		selectDeliverReg(map, model, request);
		
		return "/edi/delivery/PEDMDLY0003";
	}
	
}
