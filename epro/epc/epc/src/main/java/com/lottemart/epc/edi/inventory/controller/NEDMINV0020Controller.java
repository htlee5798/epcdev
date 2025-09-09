package com.lottemart.epc.edi.inventory.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.inventory.model.NEDMINV0020VO;
import com.lottemart.epc.edi.inventory.service.NEDMINV0020Service;

/**
 * @Class Name : NEDMINV0020Controller
 * @Description : 재고정보 현재고(상품) 조회 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.18	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Controller
public class NEDMINV0020Controller {

	@Autowired
	private NEDMINV0020Service nedminv0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//현재고(점포)
	@RequestMapping(value = "/edi/inventory/NEDMINV0020.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String store(Locale locale,  ModelMap model,HttpServletRequest request, NEDMINV0020VO nEDMINV0020VO) {
		String srchStartDate = StringUtils.trimToEmpty(request.getParameter("srchStartDate"));
		
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
				
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		if(nEDMINV0020VO.getIsForwarding() != null && "Y".equals(nEDMINV0020VO.getIsForwarding())){
			map.put("srchStartDate", srchStartDate);
			map.put("srchEndDate", srchStartDate);
		
			map.put("isForwarding",  nEDMINV0020VO.getIsForwarding());		
		}else{
			map.put("srchStartDate", commonUtil.firstDate(commonUtil.nowDateBack(nowDate)));
			map.put("srchEndDate", commonUtil.nowDateBack(nowDate));
		}
		
		model.addAttribute("paramMap",	map);
		model.addAttribute("nowHour",	nowHour);
		
		return "/edi/inventory/NEDMINV0020";
	}
	
	//현재고(점포)
	@RequestMapping(value = "/edi/inventory/NEDMINV0020Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectPeriod(ModelMap model, @RequestBody NEDMINV0020VO nEDMINV0020VO, HttpServletRequest request) throws Exception {
		
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		
		/*Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			resultMap.put("isForwarding", "Y");
		}*/
		
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMINV0020VO.setVenCds(epcLoginVO.getVendorId());
		
		List<NEDMINV0020VO>	resultList 	= 	nedminv0020Service.selectProductInfo(nEDMINV0020VO);
			
		resultMap.put("resultList", resultList);
		
		return resultMap;
	}
	
	//현재고(점포) txt 파일 생성
	@RequestMapping(value = "/edi/inventory/NEDMINV0020Text.do", method = RequestMethod.POST)
	public void createTextProduct(NEDMINV0020VO nEDMINV0020VO,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMINV0020VO.setVenCds(epcLoginVO.getVendorId());
				
		nedminv0020Service.createTextProduct(nEDMINV0020VO,request,response);
		
	}
	
	//현재고(상품) 점포별 txt파일 생성
	@RequestMapping(value = "/edi/inventory/NEDMINV0020Text2.do", method = RequestMethod.POST)
	public void createTextProductText(NEDMINV0020VO nEDMINV0020VO,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMINV0020VO.setVenCds(epcLoginVO.getVendorId());
		
		
		
		nedminv0020Service.createTextProductText(nEDMINV0020VO,request,response);
	}
}
