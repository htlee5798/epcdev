package com.lottemart.epc.edi.consult.controller;



import java.util.Locale;
import java.util.Map;

import lcn.module.common.util.HashBox;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;

import com.lottemart.epc.edi.consult.service.PEDMSCT0002Service;
import com.lottemart.epc.edi.consult.service.PEDMSCT0006Service;
import com.lottemart.epc.edi.consult.service.PEDMSCT051Service;
import com.lottemart.epc.edi.comm.controller.BaseController;


@Controller
public class PEDMSCT0006Controller extends BaseController{

	@Autowired
	private PEDMSCT0006Service pedmsct0006Service;

	@Autowired
	private PEDMSCT0002Service pedmsct0002Service;

    //입점상담관리 첫페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0006.do", method = RequestMethod.GET)
	public String consultVendor(Locale locale,  ModelMap model) {

		return "/edi/consult/PEDMCST0006";
	}

	//입점업체조회  검색
	@RequestMapping(value = "/edi/consult/PEDMCST0006select.do", method = RequestMethod.POST)
	public String selectConsultVendor(@RequestParam Map<String,Object> map,  ModelMap model) throws Exception {

		HashBox vendor = pedmsct0006Service.selectConsultVendor(map); // 업체정보

		if(vendor != null){
			model.addAttribute("conList",      pedmsct0002Service.consultAdminSelectDetail((String)vendor.get("BMAN_NO")));			// 현재이력
			model.addAttribute("conList_past", pedmsct0002Service.consultAdminSelectDetailPast((String)vendor.get("BMAN_NO"))) ;    // 과거이력
			model.addAttribute("vender", vendor);
		}
		model.addAttribute("param",map);

		return "/edi/consult/PEDMCST0006";
	}

	// 패스워드 초기화
	@RequestMapping(value = "/edi/consult/PEDMCST0006UpdaePass.do", method = RequestMethod.POST)
	public String updateConsultVendorPass(@RequestParam Map<String,Object> map,  ModelMap model) throws Exception {

		map.put("venderSearch", map.get("bmanNo"));

		pedmsct0006Service.updateConsultVendorPass(map);
		model.addAttribute("vender", pedmsct0006Service.selectConsultVendor(map));


		model.addAttribute("resultMessage", getText("msg.supply.consult.admresetpassword"));  //패스워드가 사업자번호로 초기화되었습니다.
		return "/edi/consult/PEDMCST0006";
	}

	// 상담 초기화
	@RequestMapping(value = "/edi/consult/PEDMCST0002UpdateCons.do", method = RequestMethod.POST)
	public String updateConsultVendorConsult(@RequestParam Map<String,Object> map,  ModelMap model) throws Exception {
		map.put("venderSearch", map.get("bmanNo"));

		pedmsct0006Service.updateConsultVendorConsult(map);
		model.addAttribute("vender", pedmsct0006Service.selectConsultVendor(map));


		model.addAttribute("resultMessage", getText("msg.supply.consult.admresetconsult"));  //입점상담정보가 초기화 되었습니다.
		return "/edi/consult/PEDMCST0006";
	}

}
