package com.lottemart.epc.edi.payment.controller;

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
import com.lottemart.epc.edi.payment.service.PEDMPAY0020Service;


@Controller
public class PEDMPAY0020Controller {

	@Autowired
	private PEDMPAY0020Service pedmpay0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//거래실적조회
	@RequestMapping(value = "/edi/payment/PEDMPAY0021.do", method = RequestMethod.GET)
	public String credLed(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		map.put("endDate_year",    nowDateYear);
		map.put("endDate_month",   nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0021";
	}
	
	//거래실적조회
	@RequestMapping(value = "/edi/payment/PEDMPAY0021Select.do", method = RequestMethod.POST)
	public String selectCredLed(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0020Service.selectCredLedInfo(map));
		
		return "/edi/payment/PEDMPAY0021";
	}
	
	//거래실적조회 txt파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0021Text.do", method = RequestMethod.POST)
	public void createTextCredLed(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedmpay0020Service.createTextCredLed(map,request,response);
	}
	
	//점포별 거래실적
	@RequestMapping(value = "/edi/payment/PEDMPAY0022.do", method = RequestMethod.GET)
	public String credLedStoreDetail(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		map.put("endDate_year",    nowDateYear);
		map.put("endDate_month",   nowDateMonth);
		
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0022";
	}
	
	//점포별 거래실적
	@RequestMapping(value = "/edi/payment/PEDMPAY0022Select.do", method = RequestMethod.POST)
	public String selectCredLedStoreDetail(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0020Service.selectCredLedStoreDetail(map));
		
		return "/edi/payment/PEDMPAY0022";
	}
	
	//점포별 거래실적 txt파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0022Text.do", method = RequestMethod.POST)
	public void createTextCredLedStoreDetail(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedmpay0020Service.createTextCredLedStoreDetail(map,request,response);
		
	}
	
	//잔액조회
	@RequestMapping(value = "/edi/payment/PEDMPAY0023.do", method = RequestMethod.GET)
	public String credLedStore(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		map.put("endDate_year",    nowDateYear);
		map.put("endDate_month",   nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0023";
	}
	
	//잔액조회
	@RequestMapping(value = "/edi/payment/PEDMPAY0023Select.do", method = RequestMethod.POST)
	public String selectCredLedStore(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0020Service.selectCredLedStoreInfo(map));
		
		return "/edi/payment/PEDMPAY0023";
	}
	
	//잔액조회 txt파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0023Text.do", method = RequestMethod.POST)
	public void createTextCredLedStore(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedmpay0020Service.createTextCredLedStore(map,request,response);
		
	}
	
	//패밀리론
	@RequestMapping(value = "/edi/payment/PEDMPAY0024.do", method = RequestMethod.GET)
	public String familyLoan(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		return "/edi/payment/PEDMPAY0024";
	}
	
	//패밀리론
	@RequestMapping(value = "/edi/payment/PEDMPAY0024Select.do", method = RequestMethod.POST)
	public String selectFamilyLoan(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0020Service.selectFamilyLoan(map));
		
		return "/edi/payment/PEDMPAY0024";
	}
	
	//패밀리론 txt파일생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0024Text.do", method = RequestMethod.POST)
	public void createTextFamilyLoan(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		pedmpay0020Service.createTextFamilyLoan(map,request,response);
		
	}
	
	
}
