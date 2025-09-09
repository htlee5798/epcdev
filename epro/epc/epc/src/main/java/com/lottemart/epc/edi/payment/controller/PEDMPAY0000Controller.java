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
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.service.PEDMPAY0000Service;


@Controller
public class PEDMPAY0000Controller {

	@Autowired
	private PEDMPAY0000Service pedmpay0000Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//사업자 등록번호
	@RequestMapping(value = "/edi/payment/PEDMPAY0001.do", method = RequestMethod.GET)
	public String cominfor(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		return "/edi/payment/PEDMPAY0001";
	}
	
	//사업자 등록번호
	@RequestMapping(value = "/edi/payment/PEDMPAY0001Select.do", method = RequestMethod.POST)
	public String selectCominfor(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0000Service.selectCominforInfo(map));
		
		return "/edi/payment/PEDMPAY0001";
	}
	
	
	//대금결제정보
	@RequestMapping(value = "/edi/payment/PEDMPAY0002.do", method = RequestMethod.GET)
	public String paymentDay(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0002";
	}
	
	//대금결제정보
	@RequestMapping(value = "/edi/payment/PEDMPAY0002Select.do", method = RequestMethod.POST)
	public String selectPaymentDay(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0000Service.selectPaymentDayInfo(map));
		
		return "/edi/payment/PEDMPAY0002";
	}
	
	//대금결제정보 txt 파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0002Text.do", method = RequestMethod.POST)
	public void createTextPaymentDay(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		pedmpay0000Service.createTextPaymentDay(map,request,response);
		
	}
	
	//점포별 대금결제
	@RequestMapping(value = "/edi/payment/PEDMPAY0003.do", method = RequestMethod.GET)
	public String paymentStore(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0003";
	}
	
	//점포별 대금결제
	@RequestMapping(value = "/edi/payment/PEDMPAY0003Select.do", method = RequestMethod.POST)
	public String selectPaymentStore(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("paymentList", pedmpay0000Service.selectPaymentStoreInfo(map));
		
		return "/edi/payment/PEDMPAY0003";
	}
	
	//점포별 대금결제 txt 파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0003Text.do", method = RequestMethod.POST)
	public void createTextPaymentStore(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		pedmpay0000Service.createTextPaymentStore(map,request,response);
		
	}
	
	//세금계산서 정보
	@RequestMapping(value = "/edi/payment/PEDMPAY0004.do", method = RequestMethod.GET)
	public String credAgg(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0004";
	}
	
	//세금계산서 정보
	@RequestMapping(value = "/edi/payment/PEDMPAY0004Select.do", method = RequestMethod.POST)
	public String selectCredAgg(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0000Service.selectCredAggInfo(map));
		
		return "/edi/payment/PEDMPAY0004";
	}
	
	//세금계산서 정보 txt 파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0004Text.do", method = RequestMethod.POST)
	public void createTextCredAgg(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedmpay0000Service.createTextCredAgg(map,request,response);
		
	}
	
	//물류비정보
	@RequestMapping(value = "/edi/payment/PEDMPAY0005.do", method = RequestMethod.GET)
	public String logiAmt(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		map.put("endDate_year",  nowDateYear);
		map.put("endDate_month", nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY0005";
	}
	
	//물류비정보
	@RequestMapping(value = "/edi/payment/PEDMPAY0005Select.do", method = RequestMethod.POST)
	public String selectLogiAmt(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("paymentList", pedmpay0000Service.selectLogiAmtInfo(map));
		
		return "/edi/payment/PEDMPAY0005";
	}
	
	//물류비정보 txt 파일 생성
	@RequestMapping(value = "/edi/payment/PEDMPAY0005Text.do", method = RequestMethod.POST)
	public void createTextLogiAmt(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		pedmpay0000Service.createTextLogiAmt(map,request,response);
		
	}
	
	@RequestMapping(value = "/edi/payment/PEDMPAY0006.do", method = RequestMethod.GET)
	public String promoSale(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		return "/edi/payment/PEDMPAY0006";
	}
	
	@RequestMapping(value = "/edi/payment/PEDMPAY00061.do", method = RequestMethod.GET)
	public String newPromoSale(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
	
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/PEDMPAY00061";
	}
	
	@RequestMapping(value = "/edi/payment/PEDMPAY0006Select.do", method = RequestMethod.POST)
	public String selectPromoSale(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("paymentList", pedmpay0000Service.selectPromoSaleInfo(map));
		
		return "/edi/payment/PEDMPAY0006";
	}
	
	
	@RequestMapping(value = "/edi/payment/PEDMPAY00061Select.do", method = RequestMethod.POST)
	public String selectNewPromoSale(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		//System.out.println("StoreVal is : " +map.get("storeVal").toString());
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
		model.addAttribute("paymentList", pedmpay0000Service.selectPromoNewSaleInfo(map));
		
		return "/edi/payment/PEDMPAY00061";
	}
	
	
}
