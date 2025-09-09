package com.lottemart.epc.edi.sale.controller;

import java.util.Calendar;
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
import com.lottemart.epc.edi.sale.service.PEDMSAL0000Service;

import org.springframework.util.StopWatch;

@Controller
public class PEDMSAL0000Controller {

	@Autowired
	private PEDMSAL0000Service pedmsal0000Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//매출정보(일자별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0001.do", method = RequestMethod.GET)
	public String day(Locale locale,  ModelMap model,HttpServletRequest request) {
		
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
		
		return "/edi/sale/PEDMSAL0001";
	}
	
	//매출정보(일자별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0001Select.do", method = RequestMethod.POST)
	public String selectDay(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return "/edi/sale/PEDMSAL0001";
		}
		String[] tmpDate = map.get("endDate").toString().split("-");
		map.put("startDate", tmpDate[0]+"-"+tmpDate[1]+"-01");
		
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
		
		//StopWatch watch = new StopWatch();
		model.addAttribute("paramMap",map);
	//	watch.start();
		model.addAttribute("saleList", pedmsal0000Service.selectDayInfo(map));
	//	watch.stop();
		//System.out.println("<<<totaltime>>"+watch.getTotalTimeMillis());
		return "/edi/sale/PEDMSAL0001";
	}
	
	//매출정보(일자별) txt파일 생성
	@RequestMapping(value = "/edi/sale/PEDMSAL0001Text.do", method = RequestMethod.POST)
	public void createTextDay(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] tmpDate = map.get("endDate").toString().split("-");
		map.put("startDate", tmpDate[0]+"-"+tmpDate[1]+"-01");
		
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
		
		pedmsal0000Service.createTextDay(map,request,response);
	}
	
	//매출정보(점포별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0002.do", method = RequestMethod.GET)
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
		map.put("ven", ven);
		
		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/sale/PEDMSAL0002";
	}
	
	//매출정보(점포별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0002Select.do", method = RequestMethod.POST)
	public String selectStore(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return "/edi/sale/PEDMSAL0002";
		}
		String[] tmpDate = map.get("endDate").toString().split("-");
		map.put("startDate", tmpDate[0]+"-"+tmpDate[1]+"-01");
		
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
		model.addAttribute("saleList", pedmsal0000Service.selectStoreInfo(map));
		
		return "/edi/sale/PEDMSAL0002";
	}
	
	//매출정보(점포별) txt파일 생성
	@RequestMapping(value = "/edi/sale/PEDMSAL0002Text.do", method = RequestMethod.POST)
	public void createTextStore(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] tmpDate = map.get("endDate").toString().split("-");
		map.put("startDate", tmpDate[0]+"-"+tmpDate[1]+"-01");
		
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
		
		pedmsal0000Service.createTextStore(map,request,response);
	}
	
	//매출정보(상품별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0003.do", method = RequestMethod.GET)
	public String product(Locale locale,  ModelMap model,HttpServletRequest request) {
		
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
		
		return "/edi/sale/PEDMSAL0003";
	}
	
	//매출정보(상품별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0003Select.do", method = RequestMethod.POST)
	public String selectProduct(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return "/edi/sale/PEDMSAL0003";
		}
		
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
		model.addAttribute("saleList", pedmsal0000Service.selectProductInfo(map));
		
		return "/edi/sale/PEDMSAL0003";
	}
	
	//매출정보(상품별) txt파일 생성
	@RequestMapping(value = "/edi/sale/PEDMSAL0003Text.do", method = RequestMethod.POST)
	public void createTextProduct(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedmsal0000Service.createTextProduct(map,request,response);
	}

	//매출정보(상품상세별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0004.do", method = RequestMethod.GET)
	public String productDetail(Locale locale,  ModelMap model,HttpServletRequest request) {
		
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
		
		return "/edi/sale/PEDMSAL0004";
	}
	
	//매출정보(상품상세별)
	@RequestMapping(value = "/edi/sale/PEDMSAL0004Select.do", method = RequestMethod.POST)
	public String selectProductDetail(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return "/edi/sale/PEDMSAL0004";
		}
		
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
		model.addAttribute("saleList", pedmsal0000Service.selectProductDetailInfo(map));
		
		return "/edi/sale/PEDMSAL0004";
	}
	
	//매출정보(상품상세별) txt파일 생성
	@RequestMapping(value = "/edi/sale/PEDMSAL0004Text.do", method = RequestMethod.POST)
	public void createTextProductDetail(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedmsal0000Service.createTextProductDetail(map,request,response);
	}
	
}
