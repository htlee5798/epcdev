package com.lottemart.epc.edi.consult.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.sendEmsSmsService;
import com.lottemart.epc.edi.consult.model.NEDMCST0030VO;
import com.lottemart.epc.edi.consult.service.NEDMCST0030Service;


/**
 * 협업정보 - > 협업정보  - > 알리미 서비스  Controller
 * 
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */
@Controller
public class NEDMCST0030Controller {

	@Autowired
	private NEDMCST0030Service nedmcst0030Service;
	
	@Autowired
	private sendEmsSmsService sendemssmsService;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 페이지  
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0030.do", method = RequestMethod.GET)
	public String alertPage(Locale locale,  ModelMap model) {
		return "/edi/consult/NEDMCST0030";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 등록 조회
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0031select.do", method = {RequestMethod.POST,RequestMethod.GET})
	public String alertPageInsertPageSelect(NEDMCST0030VO map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setBmans(epcLoginVO.getCono());
		
		String[] tmp = epcLoginVO.getCono();
		String bman="";
		String business = epcLoginVO.getLoginNm();
		
		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
		
		model.addAttribute("paramMap",map);
		model.addAttribute("bman",bman);
		model.addAttribute("business",business);
		model.addAttribute("alertList",nedmcst0030Service.alertPageInsertPageSelect(map));
		
		return "/edi/consult/NEDMCST0031";
	}
	
		/**
		 *  협업정보 - > 협업정보  - > 알리미 등록 조회
		 * @param NEDMCST0030VO
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/consult/NEDMCST0031select.json", method = RequestMethod.POST)
		public @ResponseBody Map<String, Object> NEDMCST0031select(@RequestBody NEDMCST0030VO map,HttpServletRequest request) throws Exception{
			
			// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			map.setBmans(epcLoginVO.getCono());
			String business = epcLoginVO.getLoginNm();
			Map<String, Object>	resultMap	=	new HashMap<String, Object>();
			List<NEDMCST0030VO> 	alertList 	= 	nedmcst0030Service.alertPageInsertPageSelect(map);
			
			resultMap.put("alertList", alertList);
			resultMap.put("business", business);
			return resultMap;
		}
	
	
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 등록 조회
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0032forwardInsertPage.do", method = RequestMethod.POST)
	public String alertPageForwarding(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String[] tmp = epcLoginVO.getCono();
		String bman="";
		
		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
		
		model.addAttribute("bman",bman);
		
		
		return "/edi/consult/NEDMCST0032";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 이메일 체크  AJAX
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/getEmailCount.json")
    public ModelAndView ajaxEmailCk(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = nedmcst0030Service.ajaxEmailCk(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 이메일 수정 체크  AJAX
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/getEmailCountUP.json")
    public ModelAndView ajaxEmailCkUP(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = nedmcst0030Service.ajaxEmailCkUP(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 핸드폰 체크  AJAX
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/getCellCount.json")
    public ModelAndView ajaxCellCk(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = nedmcst0030Service.ajaxCellCk(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 핸드폰 수정 체크  AJAX
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/getCellCountUP.json")
    public ModelAndView ajaxCellCkUP(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		
		String state = "T";	
		Integer  cnt = nedmcst0030Service.ajaxCellCkUP(map);
		if(cnt > 0) state ="F";
		
		return AjaxJsonModelHelper.create(state);
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 협력업체 코드  AJAX
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/vendorSetting.json")
    public ModelAndView ajaxVendor(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		
		String state = "";	
		list = nedmcst0030Service.ajaxVendor(map);

		for(int i=0;i<list.size();i++){
			hmap = (HashMap<String, Object>) list.get(i);
			state += hmap.get("VENDOR_ID").toString() + ";";
		}
		
		return AjaxJsonModelHelper.create(state);
	}
	
	//협력업체 코드 checkd AJAX
	@RequestMapping(value = "/edi/consult/ckBmanValue.json")
    public ModelAndView ajaxVendorCK(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		
		String state = "";	
		list = nedmcst0030Service.ajaxVendorCK(map);

		for(int i=0;i<list.size();i++){
			hmap = (HashMap<String, Object>) list.get(i);
			state += hmap.get("VENDOR_ID").toString() + ";";
		}
		return AjaxJsonModelHelper.create(state);
	}
	
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 등록 
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0032Insert.do", method = RequestMethod.POST)
	public String alertPageInsert(NEDMCST0030VO map , HttpServletRequest request, HttpServletResponse response) throws Exception{
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setLoginId(epcLoginVO.getAdminId());
		
		String svc_seq = "";
		
		svc_seq = nedmcst0030Service.alertPageInsert(map);
		
		HashMap<String, Object> hmap = new HashMap();
		hmap.put("V_BMAN_NO",map.getBmanNo());
		hmap.put("V_ID","");
		hmap.put("V_AUTHO_FG","");
		hmap.put("V_TEL_NO","");
		hmap.put("V_EMAIL","");
		hmap.put("V_SUB_INFO_ID","");
		hmap.put("V_EMS_CD","01");
		hmap.put("V_SVC_SEQ",svc_seq);
		
		hmap.put("V_VENDOR_ID","");
		hmap.put("V_ANX_INFO_CD",map.getAnxInfoCd());
		hmap.put("V_DEST_CALL_NO","");
		hmap.put("V_MSGS","");
		hmap.put("V_SMS_CD","01");
		
		hmap.put("V_MSG","");
		hmap.put("V_ERR","");
		hmap.put("V_LOG","");
		
		sendemssmsService.sendEMS(hmap);
		sendemssmsService.sendSMS(hmap);
		
		return "redirect:/edi/consult/NEDMCST0031select.do";
	}
	
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 수정  페이지
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0033forwardUpdatePage.do", method = RequestMethod.POST)
	public String alertPageUpdatePage(NEDMCST0030VO map,  ModelMap model,HttpServletRequest request) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] tmp = epcLoginVO.getCono();
		String bman="";
		
		for(int i=0;i<tmp.length;i++){
			bman += tmp[i] + ";";
		}
		
		model.addAttribute("bman",bman);
		model.addAttribute("alertInfo",nedmcst0030Service.alertPageUpdatePage(map));
		
		return "/edi/consult/NEDMCST0033";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 업데이트
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0032Update.do", method = RequestMethod.POST)
	public String alertPageUpdate(NEDMCST0030VO map , HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setLoginId(epcLoginVO.getAdminId());
		
		nedmcst0030Service.alertPageUpdate(map);
		
		String new_tel_no1 = map.getCell1();
		String new_tel_no2 = map.getCell2();
		String new_tel_no3 = map.getCell3();
		String new_tel = new_tel_no1.replaceAll(" ","")+new_tel_no2.replaceAll(" ","")+new_tel_no3.replaceAll(" ","");
		
		String old_tel_no1 = map.getOldCell1();
		String old_tel_no2 = map.getOldCell2();
		String old_tel_no3 = map.getOldCell3();
		String old_tel = old_tel_no1.replaceAll(" ","")+old_tel_no2.replaceAll(" ","")+old_tel_no3.replaceAll(" ","");
		
		
		
		String old_email = map.getOldEmail();
		String new_email = map.getEmail();
		
		HashMap<String, Object> hmap = new HashMap();
		hmap.put("V_BMAN_NO",map.getBmanNo());
		hmap.put("V_ID","");
		hmap.put("V_AUTHO_FG","");
		hmap.put("V_TEL_NO","");
		hmap.put("V_EMAIL","");
		hmap.put("V_SUB_INFO_ID","");
		hmap.put("V_EMS_CD","01");
		hmap.put("V_SVC_SEQ",map.getSvcSeq());
		
		hmap.put("V_VENDOR_ID","");
		hmap.put("V_ANX_INFO_CD",map.getAnxInfoCd());
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
		
		return "redirect:/edi/consult/NEDMCST0031select.do";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 삭제
	 * @param NEDMCST0030VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0032delete.do", method = RequestMethod.POST)
	public String alertPageDelete(NEDMCST0030VO map , HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		nedmcst0030Service.alertPageDelete(map);
		
		return "redirect:/edi/consult/NEDMCST0031select.do";
	}
	
	
	
    
}


