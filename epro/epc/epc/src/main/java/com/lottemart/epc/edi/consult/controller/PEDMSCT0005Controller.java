package com.lottemart.epc.edi.consult.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.service.PEDMSCT0005Service;
import com.lottemart.epc.edi.comm.service.sendEmsSmsService;



@Controller
public class PEDMSCT0005Controller {

	@Autowired
	private PEDMSCT0005Service pedmsct0005Service;
	
	@Autowired
	private sendEmsSmsService sendemssmsService;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
    //알리미 페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0005.do", method = RequestMethod.GET)
	public String alertPage(Locale locale,  ModelMap model) {
		return "/edi/consult/PEDMCST0005";
	}
	
	//알리미 등록 조회
	@RequestMapping(value = "/edi/consult/PEDMCST0005select.do", method = RequestMethod.POST)
	public String alertPageInsertPageSelect(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("bmans", epcLoginVO.getCono());
		
		String[] tmp = epcLoginVO.getCono();
		String bman="";
		String business = epcLoginVO.getLoginNm();
		
		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("bman",bman);
		model.addAttribute("business",business);
		model.addAttribute("alertList",pedmsct0005Service.alertPageInsertPageSelect(map));
		
		return "/edi/consult/PEDMCST000501";
	}
	
	//알리미 등록 조회
	@RequestMapping(value = "/edi/consult/forwardInsertPage.do", method = RequestMethod.POST)
	public String alertPageForwarding(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String[] tmp = epcLoginVO.getCono();
		String bman="";
		String business = epcLoginVO.getLoginNm();
		
		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("bman",bman);
		model.addAttribute("business",business);
		
		model.addAttribute("paramMap",map);
		model.addAttribute("venList", pedmsct0005Service.selectVenCd(map));
		
		return "/edi/consult/PEDMCST000502";
	}
	
	//이메일 체크  AJAX
	@RequestMapping(value = "/edi/consult/getEmailCount.do")
    public ModelAndView ajaxEmailCk(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = pedmsct0005Service.ajaxEmailCk(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	//이메일 수정 체크  AJAX
	@RequestMapping(value = "/edi/consult/getEmailCountUP.do")
    public ModelAndView ajaxEmailCkUP(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = pedmsct0005Service.ajaxEmailCkUP(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	//핸드폰 체크  AJAX
	@RequestMapping(value = "/edi/consult/getCellCount.do")
    public ModelAndView ajaxCellCk(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = pedmsct0005Service.ajaxCellCk(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	//핸드폰 수정 체크  AJAX
	@RequestMapping(value = "/edi/consult/getCellCountUP.do")
    public ModelAndView ajaxCellCkUP(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = pedmsct0005Service.ajaxCellCkUP(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	//협력업체 코드  AJAX
	@RequestMapping(value = "/edi/consult/vendorSetting.do")
    public ModelAndView ajaxVendor(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		
		String state = "";	
		list = pedmsct0005Service.ajaxVendor(map);

		for(int i=0;i<list.size();i++){
			hmap = (HashMap<String, Object>) list.get(i);
			state += hmap.get("VENDOR_ID").toString() + ";";
		}
		
		return AjaxJsonModelHelper.create(state);
	}
	
	//협력업체 코드 checkd AJAX
	@RequestMapping(value = "/edi/consult/ckBmanValue.do")
    public ModelAndView ajaxVendorCK(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		
		String state = "";	
		list = pedmsct0005Service.ajaxVendorCK(map);

		for(int i=0;i<list.size();i++){
			hmap = (HashMap<String, Object>) list.get(i);
			state += hmap.get("VENDOR_ID").toString() + ";";
		}
		
		return AjaxJsonModelHelper.create(state);
	}
	
	
	//알리미 등록
	@RequestMapping(value = "/edi/consult/PEDMCST000502Insert.do", method = RequestMethod.POST)
	public String alertPageInsert(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		map.put("loginId", epcLoginVO.getAdminId());
		
		String svc_seq = "";
		
		svc_seq = pedmsct0005Service.alertPageInsert(map);
		
		HashMap<String, Object> hmap = new HashMap();
		hmap.put("V_BMAN_NO",map.get("sele_bman").toString());
		hmap.put("V_ID","");
		hmap.put("V_AUTHO_FG","");
		hmap.put("V_TEL_NO","");
		hmap.put("V_EMAIL","");
		hmap.put("V_SUB_INFO_ID","");
		hmap.put("V_EMS_CD","01");
		hmap.put("V_SVC_SEQ",svc_seq);
		
		hmap.put("V_VENDOR_ID","");
		hmap.put("V_ANX_INFO_CD",map.get("sele_service").toString());
		hmap.put("V_DEST_CALL_NO","");
		hmap.put("V_MSGS","");
		hmap.put("V_SMS_CD","01");
		
		hmap.put("V_MSG","");
		hmap.put("V_ERR","");
		hmap.put("V_LOG","");
		
		sendemssmsService.sendEMS(hmap);
		sendemssmsService.sendSMS(hmap);
		
		return alertPageInsertPageSelect(map,model,request);
	}
	
	
	//알리미 수정  페이지
	@RequestMapping(value = "/edi/consult/forwardUpdatePage.do", method = RequestMethod.POST)
	public String alertPageUpdatePage(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] tmp = epcLoginVO.getCono();
		String bman="";
		String business = epcLoginVO.getLoginNm();
		
		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
		
		model.addAttribute("bman",bman);
		model.addAttribute("business",business);
		model.addAttribute("alertList",pedmsct0005Service.alertPageUpdatePage(map));
		
		return "/edi/consult/PEDMCST000503";
	}
	
	//알리미 업데이트
	@RequestMapping(value = "/edi/consult/PEDMCST000502Update.do", method = RequestMethod.POST)
	public String alertPageUpdate(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		map.put("loginId", epcLoginVO.getAdminId());
		
		pedmsct0005Service.alertPageUpdate(map);
		
		String old_tel = map.get("compare_tel").toString().replaceAll(" ","");
		String new_tel_no1 = map.get("cell1").toString();
		String new_tel_no2 = map.get("cell2").toString();
		String new_tel_no3 = map.get("cell3").toString();
		String new_tel = new_tel_no1.replaceAll(" ","")+new_tel_no2.replaceAll(" ","")+new_tel_no3.replaceAll(" ","");
		String old_email = map.get("compare_email").toString();
		String new_email = map.get("email").toString();
		
		HashMap<String, Object> hmap = new HashMap();
		hmap.put("V_BMAN_NO",map.get("sele_bman").toString());
		hmap.put("V_ID","");
		hmap.put("V_AUTHO_FG","");
		hmap.put("V_TEL_NO","");
		hmap.put("V_EMAIL","");
		hmap.put("V_SUB_INFO_ID","");
		hmap.put("V_EMS_CD","01");
		hmap.put("V_SVC_SEQ",map.get("seq_no".toString()));
		
		hmap.put("V_VENDOR_ID","");
		hmap.put("V_ANX_INFO_CD",map.get("sele_service").toString());
		hmap.put("V_DEST_CALL_NO","");
		hmap.put("V_MSGS","");
		hmap.put("V_SMS_CD","01");
		
		hmap.put("V_MSG","");
		hmap.put("V_ERR","");
		hmap.put("V_LOG","");
		
		if(!old_tel.equals(new_tel)){
			sendemssmsService.sendSMS(hmap);
		}
		if(!old_email.equals(new_email)){
			sendemssmsService.sendEMS(hmap);
		} 
		
		return alertPageInsertPageSelect(map,model,request);
	}
	
	//알리미 삭제
	@RequestMapping(value = "/edi/consult/PEDMCST000502delete.do", method = RequestMethod.POST)
	public String alertPageDelete(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		map.put("loginId", epcLoginVO.getAdminId());
		
		pedmsct0005Service.alertPageDelete(map);
		
		return alertPageInsertPageSelect(map,model,request);
	}
	
	
	
	
	
    
}


