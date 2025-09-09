package com.lottemart.epc.edi.consult.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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

import com.lottemart.epc.edi.comm.service.sendEmsSmsService;


@Controller
public class PEDMSCT0011Controller {

	@Autowired
	private sendEmsSmsService sendemssmsService;

	//신문고 첫페이지
	@RequestMapping(value = "/edi/consult/PEDPCST0011.do", method = RequestMethod.GET)
	public String cyber(Locale locale,  ModelMap model) {

		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);

		model.addAttribute("paramMap",map);

		return "/edi/consult/PEDPCST0011";
	}

	//신문고 입력 페이지
	@RequestMapping(value = "/edi/consult/PEDPCST001101.do", method = RequestMethod.GET)
	public String cyberInsertPage(Locale locale,  ModelMap model) {

		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);

		model.addAttribute("paramMap",map);

		return "/edi/consult/PEDPCST001101";
	}

	//신문고 입력
	@RequestMapping(value = "/edi/consult/PEDPCST001101Insert.do", method = RequestMethod.POST)
	public String orderStopResultSelect(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {


		String reg_name=map.get("reg_name").toString();
		String reg_email=map.get("reg_email").toString();
		String title=map.get("title").toString();
		String content=map.get("coment").toString();
		String name=map.get("v_name").toString();
		String email=map.get("v_email").toString();

		map.put("V_BMAN_NO",reg_name);
		map.put("V_ID",reg_email);
		map.put("V_AUTHO_FG",title);
		map.put("V_TEL_NO",content);
		map.put("V_EMAIL",name);
		map.put("V_SUB_INFO_ID",email);
		map.put("V_EMS_CD","99");
		map.put("V_SVC_SEQ","");

		map.put("V_MSG","");
		map.put("V_ERR","");
		map.put("V_LOG","");

		sendemssmsService.sendEMS(map);

		model.addAttribute("emsResult","exist");

		return "/edi/consult/PEDPCST001101";
	}



}
