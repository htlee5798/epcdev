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
import com.lottemart.epc.edi.inventory.model.NEDMINV0030VO;
import com.lottemart.epc.edi.inventory.service.NEDMINV0030Service;

/**
 * @Class Name : NEDMINV0030Controller
 * @Description : 재고정보 센터 점출입 조회 Controller Class
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
public class NEDMINV0030Controller {

	@Autowired
	private NEDMINV0030Service nedminv0030Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	//센터점출입
	@RequestMapping(value = "/edi/inventory/NEDMINV0030.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String store(Locale locale,  ModelMap model,HttpServletRequest request, NEDMINV0030VO nEDMINV0030VO) {
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
		
		
		map.put("srchStartDate", commonUtil.firstDate(commonUtil.nowDateBack(nowDate)));
		map.put("srchEndDate", commonUtil.nowDateBack(nowDate));
		
		
		model.addAttribute("paramMap",map);
		
		return "/edi/inventory/NEDMINV0030";
	}
	
	//센터점출입
	@RequestMapping(value = "/edi/inventory/NEDMINV0030Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectPeriod(ModelMap model, @RequestBody NEDMINV0030VO nEDMINV0030VO, HttpServletRequest request) throws Exception {
		
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			resultMap.put("isForwarding", "Y");
		}
		
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMINV0030VO.setVenCds(epcLoginVO.getVendorId());
		
		List<NEDMINV0030VO>	resultList 	= 	nedminv0030Service.selectCenterStoreInfo(nEDMINV0030VO);
			
		resultMap.put("resultList", resultList);
		
		return resultMap;
	}
	
	//센터점출입 txt 파일 생성
	@RequestMapping(value = "/edi/inventory/NEDMINV0030Text.do", method = RequestMethod.POST)
	public void createTextProduct(NEDMINV0030VO nEDMINV0030VO,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMINV0030VO.setVenCds(epcLoginVO.getVendorId());
				
		nedminv0030Service.createTextCenterStore(nEDMINV0030VO,request,response);
		
	}

}
