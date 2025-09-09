package com.lottemart.epc.edi.order.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;


import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.order.service.PEDMORD0000Service;


@Controller
public class PEDMORD0000Controller {
	private static final Logger logger = LoggerFactory.getLogger(PEDMORD0000Controller.class);

	@Autowired
	private PEDMORD0000Service pedmord0000Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//발주정보(상품별) 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0001.do", method = RequestMethod.GET)
	public String period(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		
		
		return "/edi/order/PEDMORD0001";
	}
	
	//발주정보(상품별) 조회
	@RequestMapping(value = "/edi/order/PEDMORD0001Select.do", method = RequestMethod.POST)
	public String selectPeriod(@RequestParam Map<String,Object> map, ModelMap model ,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		
		model.addAttribute("paramMap",map);
		model.addAttribute("orderList", pedmord0000Service.selectPeriodInfo(map));
		return "/edi/order/PEDMORD0001";
		
	}
	
	//발주정보(상품별) txt파일 생성
	@RequestMapping(value = "/edi/order/PEDMORD0001Text.do", method = RequestMethod.POST)
    public void createTextPeriod(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		pedmord0000Service.createTextPeriod(map,request,response);
	}
	
	//발주정보(전표별) 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0002.do", method = RequestMethod.GET)
	public String junpyo(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/order/PEDMORD0002";
	}
	
	//발주정보(전표별) 조회
	@RequestMapping(value = "/edi/order/PEDMORD0002Select.do", method = RequestMethod.POST)
	public String selectJunpyo(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		model.addAttribute("paramMap",map);
		model.addAttribute("orderList", pedmord0000Service.selectJunpyoInfo(map));
		
		return "/edi/order/PEDMORD0002";
	}
	
	//발주정보(전표별) txt파일 생성
	@RequestMapping(value = "/edi/order/PEDMORD0002Text.do", method = RequestMethod.POST)
	public void createTextJunpyo(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		pedmord0000Service.createTextJunpyo(map,request,response);
	}
	
	
	//발주정보(전표상세) 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0003.do", method = RequestMethod.GET)
	public String junpyoDetail(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/order/PEDMORD0003";
	}
	
	//발주정보(전표상세) 조회
	@RequestMapping(value = "/edi/order/PEDMORD0003Select.do", method = RequestMethod.POST)
	public String selectJunpyoDetail(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		model.addAttribute("paramMap",map);
		model.addAttribute("orderList", pedmord0000Service.selectJunpyoDetailInfo(map));
		
		return "/edi/order/PEDMORD0003";
	}
	
	//발주정보(전표상세) txt파일 생성
	@RequestMapping(value = "/edi/order/PEDMORD0003Text.do", method = RequestMethod.POST)
	public void createTextJunpyoDetail(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		pedmord0000Service.createTextJunpyoDetail(map,request,response);
		
	}
	
	//발주정보(점포별) 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0004.do", method = RequestMethod.GET)
	public String store(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/order/PEDMORD0004";
	}
	
	//발주정보(점포별) 조회
	@RequestMapping(value = "/edi/order/PEDMORD0004Select.do", method = RequestMethod.POST)
	public String selectStore(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
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
		model.addAttribute("orderList", pedmord0000Service.selectStoreInfo(map));
		
		return "/edi/order/PEDMORD0004";
	}
	
	//발주정보(점포별) txt파일 생성
	@RequestMapping(value = "/edi/order/PEDMORD0004Text.do", method = RequestMethod.POST)
	public void createTextStore(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
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
		
		pedmord0000Service.createTextStore(map,request,response);
	}
	
	//발주정보(PDC전표상세) 첫페이지
	@RequestMapping(value = "/edi/order/PEDMORD0005.do", method = RequestMethod.GET)
	public String junpyoDetailPDC(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		//Defalut 날짜 입력(현재날짜)
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/order/PEDMORD0005";
	}
	
	//발주정보(PDC전표상세) 조회
	@RequestMapping(value = "/edi/order/PEDMORD0005Select.do", method = RequestMethod.POST)
	public String selectJunpyoDetailPDC(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		model.addAttribute("paramMap",map);
		model.addAttribute("orderList", pedmord0000Service.selectJunpyoDetailInfoPDC(map));
		
		return "/edi/order/PEDMORD0005";
	}
	
	//발주정보(PDC전표상세) txt파일생성
	@RequestMapping(value = "/edi/order/PEDMORD0005Text.do", method = RequestMethod.POST)
	public void createTextJunpyoDetailPDC(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		pedmord0000Service.createTextJunpyoDetailPDC(map,request,response);
		
	}
	
	public static void main(String[] args) {
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		logger.debug(nowDate);
	}
}
