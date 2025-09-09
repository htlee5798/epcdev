package com.lottemart.epc.edi.inventory.controller;

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
import com.lottemart.epc.edi.inventory.service.PEDMINV0000Service;


@Controller
public class PEDMINV0000Controller {

	@Autowired
	private PEDMINV0000Service pedminv0000Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//현재고(점포)
	@RequestMapping(value = "/edi/inventory/PEDMINV0001.do", method = RequestMethod.GET)
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
		
		map.put("startDate", commonUtil.firstDate(commonUtil.nowDateBack(nowDate)));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/inventory/PEDMINV0001";
	}
	
	//현재고(점포)
	@RequestMapping(value = "/edi/inventory/PEDMINV0001Select.do", method = RequestMethod.POST)
	public String selectPeriod(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return "/edi/sale/PEDMSAL0001";
		}
		
		String[] stors = map.get("storeVal").toString().split("-");
		String[] dateTmp = map.get("endDate").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("stk_mm", dateTmp[0]+dateTmp[1]);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("inventoryList", pedminv0000Service.selectStoreInfo(map));
		
		return "/edi/inventory/PEDMINV0001";
	}
	
	//현재고(점포) txt 파일 생성
	@RequestMapping(value = "/edi/inventory/PEDMINV0001Text.do", method = RequestMethod.POST)
	public void createTextPeriod(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		String[] dateTmp = map.get("endDate").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("stk_mm", dateTmp[0]+dateTmp[1]);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		pedminv0000Service.createTextPeriod(map,request,response);
		
	}
	
	
	//현재고(상품) 
	@RequestMapping(value = "/edi/inventory/PEDMINV0002.do", method = RequestMethod.GET)
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
		
		map.put("startDate", commonUtil.firstDate(commonUtil.nowDateBack(nowDate)));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",map);
		
		return "/edi/inventory/PEDMINV0002";
	}
	
	//현재고(상품)
	@RequestMapping(value = "/edi/inventory/PEDMINV0002Select.do", method = RequestMethod.POST)
	public String selectProduct(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return "/edi/sale/PEDMSAL0001";
		}
		
		String[] stors = map.get("storeVal").toString().split("-");
		String[] dateTmp = map.get("endDate").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("stk_mm", dateTmp[0]+dateTmp[1]);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("inventoryList", pedminv0000Service.selectProductInfo(map));
		
		return "/edi/inventory/PEDMINV0002";
	}
	
	//현재고(상품) txt파일 생성
	@RequestMapping(value = "/edi/inventory/PEDMINV0002Text.do", method = RequestMethod.POST)
	public void createTextProduct(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		String[] dateTmp = map.get("endDate").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("stk_mm", dateTmp[0]+dateTmp[1]);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		pedminv0000Service.createTextProduct(map,request,response);
	}
	
	//현재고(상품) 점포별 txt파일 생성
	@RequestMapping(value = "/edi/inventory/PEDMINV0002Text2.do", method = RequestMethod.POST)
	public void createTextProductText(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] stors = map.get("storeVal").toString().split("-");
		String[] dateTmp = map.get("endDate").toString().split("-");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		map.put("stk_mm", dateTmp[0]+dateTmp[1]);
		
		if(!"".equals(stors[0])){
			map.put("storeVal", stors);
		}
		
		pedminv0000Service.createTextProductText(map,request,response);
	}
	
	//센터점출입
	@RequestMapping(value = "/edi/inventory/PEDMINV0003.do", method = RequestMethod.GET)
	public String centerStore(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		return "/edi/inventory/PEDMINV0003";
	}
	
	//센터점출입
	@RequestMapping(value = "/edi/inventory/PEDMINV0003Select.do", method = RequestMethod.POST)
	public String selectCenterStore(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("inventoryList", pedminv0000Service.selectCenterStoreInfo(map));
		
		return "/edi/inventory/PEDMINV0003";
	}
	
	//센터점출입 txt 파일 생성
	@RequestMapping(value = "/edi/inventory/PEDMINV0003Text.do", method = RequestMethod.POST)
	public void createTextCenterStore(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedminv0000Service.createTextCenterStore(map,request,response);
	}
	
	//센터점출입상세
	@RequestMapping(value = "/edi/inventory/PEDMINV0004.do", method = RequestMethod.GET)
	public String centerStoreDetail(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		return "/edi/inventory/PEDMINV0004";
	}
	
	//센터점출입상세
	@RequestMapping(value = "/edi/inventory/PEDMINV0004Select.do", method = RequestMethod.POST)
	public String selectCenterStoreDetail(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
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
		model.addAttribute("inventoryList", pedminv0000Service.selectCenterStoreDetailInfo(map));
		
		return "/edi/inventory/PEDMINV0004";
	}
	
	//센터점출입상세 txt파일 생성
	@RequestMapping(value = "/edi/inventory/PEDMINV0004Text.do", method = RequestMethod.POST)
	public void createTextCenterStoreDetail(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		pedminv0000Service.createTextCenterStoreDetail(map,request,response);
	}
	
}
