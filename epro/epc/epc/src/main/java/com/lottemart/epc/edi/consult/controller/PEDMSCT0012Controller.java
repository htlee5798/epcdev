package com.lottemart.epc.edi.consult.controller;



import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.service.PEDMSCT0012Service;


@Controller
public class PEDMSCT0012Controller {

	@Autowired
	private PEDMSCT0012Service pedmsct0012Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	// 업체 신상정보 조회 첫페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0012.do", method = RequestMethod.GET)
	public String vendorMain(Locale locale, ModelMap model,
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("bmanNoS", epcLoginVO.getCono());

		String[] tmp = epcLoginVO.getCono();
		String bman="";

		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}

		model.addAttribute("bman",bman);
		model.addAttribute("alertList",pedmsct0012Service.alertPageUpdatePageSelect(map));
		
		return "/edi/consult/PEDMCST0012";
	}
	
	
	@RequestMapping(value = "/edi/consult/PEDMCST0012Select.do", method = RequestMethod.POST)	
	public String alertPageUpdatePageSelectSearch(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("bmanNoS", epcLoginVO.getCono());
		
		String[] tmp = epcLoginVO.getCono();
		String bman="";

		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
//		System.out.println("ProfitRate : "+pscmprd0009VO.getProfitRate());
//		System.out.println("[ sele_bman : "+sele_bman +" ]");
		
		model.addAttribute("bman",bman);
		model.addAttribute("paramMap",map);
	//	model.addAttribute("conList", pedmsct0012Service.alertPageUpdatePageSelect(map));
		model.addAttribute("alertList", pedmsct0012Service.alertPageUpdatePageSelect(map));

		
		return "/edi/consult/PEDMCST0012";
	}

	@RequestMapping(value = "/edi/consult/PEDMCST0012SelectDetail.do", method = RequestMethod.POST)
	public String alertPageUpdatePageSelectDetail(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("bmanNoS", epcLoginVO.getCono());
		
		String[] tmp = epcLoginVO.getCono();
		String bman="";

		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
//		System.out.println("ProfitRate : "+pscmprd0009VO.getProfitRate());
//		System.out.println("[ sele_bman : "+sele_bman +" ]");
		
		model.addAttribute("bman",bman);
		model.addAttribute("paramMap",map);
	//	model.addAttribute("conList", pedmsct0012Service.alertPageUpdatePageSelect(map));
		model.addAttribute("alertList", pedmsct0012Service.alertPageUpdatePageSelectDetail(map));

		
		return "/edi/consult/PEDMCST00121";
	}

	
	
	//알리미 수정  페이지
	//public String alertPageUpdatePage(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
	//	// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
//		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
//		String[] tmp = epcLoginVO.getCono();
//		String bman="";
//		String business = epcLoginVO.getLoginNm();
//		
//		for(int i=0;i<tmp.length;i++){
//			bman += tmp[i] + ";";
//		}
//		
//		model.addAttribute("bman",bman);
//		model.addAttribute("business",business);
	//	model.addAttribute("alertList",pedmsct0005Service.alertPageUpdatePage(map));
		
//		return "/edi/consult/PEDMCST000503";
//	}
	
	
		
		
}














