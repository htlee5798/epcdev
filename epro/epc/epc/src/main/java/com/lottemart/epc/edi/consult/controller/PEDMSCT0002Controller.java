package com.lottemart.epc.edi.consult.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.model.VendorSession;
import com.lottemart.epc.edi.consult.service.PEDMSCT0002Service;
import com.lottemart.epc.edi.consult.service.PEDMSCT051Service;



@Controller
public class PEDMSCT0002Controller {

	@Autowired 
	private PEDMSCT0002Service pedmsct0002Service;
	
	@Autowired 
	private PEDPCOM0001Service commService; 
	
	@Autowired
	private PEDMSCT051Service consultService;
	
    //입점상담관리 첫페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0002.do", method = RequestMethod.GET)
	public String consultAdmin(Locale locale,  ModelMap model ,SearchParam searchParam) {
		
		if(StringUtils.isEmpty(searchParam.getGroupCode())) {
			searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD_CON);
		}
		
		List comList= new ArrayList();
		comList =  pedmsct0002Service.selectL1List(searchParam);
		String compare="";
		
		if(comList.size()==0 || comList == null){
			compare="none";
		}
		
		model.addAttribute("com",compare);
		model.addAttribute("teamList", 	  pedmsct0002Service.selectDistinctTeamList());
		model.addAttribute("l1GroupList", 	   pedmsct0002Service.selectL1List(searchParam));
		
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		map.put("paperState", "all");
		
		model.addAttribute("paramMap",map);
		
		return "/edi/consult/PEDMCST0002";
	}
	
	//입점상담관리  검색
	@RequestMapping(value = "/edi/consult/PEDMCST0002select.do", method = RequestMethod.POST)
	public String consultAdminSelect(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		searchParam.setGroupCode(map.get("teamCode").toString());
		
		List comList= new ArrayList();
		comList =  pedmsct0002Service.selectL1List(searchParam);
		String compare="";
		
		if(comList.size()==0 || comList == null){
			compare="none";
		}
		
		model.addAttribute("com",compare);
		model.addAttribute("teamList", 	  	   pedmsct0002Service.selectDistinctTeamList());
		model.addAttribute("l1GroupList", 	   pedmsct0002Service.selectL1List(searchParam));
		
		int cp = Integer.parseInt(map.get("currentPage").toString());
		int ps = Integer.parseInt(map.get("pageSize").toString());
		int rp = Integer.parseInt(map.get("recordCountPerPage").toString());
		int totCnt=0;
		
		/** pageing setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
    	paginationInfo.setCurrentPageNo(cp);
    	paginationInfo.setPageSize(ps);
    	paginationInfo.setRecordCountPerPage(rp);
    	
    	List list= new ArrayList();
		list = pedmsct0002Service.consultAdminSelect(map);
    	
		model.addAttribute("paramMap",map);
		model.addAttribute("conList", pedmsct0002Service.consultAdminSelect(map));
		
		if(list.size() > 0){
			HashMap hData = (HashMap)list.get(0);
			
			totCnt = Integer.parseInt(hData.get("TOTCNT").toString());
		}
		
		
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
		
		return "/edi/consult/PEDMCST0002";
	}
	
	//입점상담관리  서류심사 통과 업데이트
	@RequestMapping(value = "/edi/consult/PEDMCST0002updatePape1.do", method = RequestMethod.POST)
	public String papeUpdate1(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		
		pedmsct0002Service.papeUpdate1(map);
		
		return consultAdminSelect(map, model, searchParam);
	}
	
	//입점상담관리  서류심사 반려 업데이트 
	@RequestMapping(value = "/edi/consult/PEDMCST0002updatePape2.do", method = RequestMethod.POST)
	public String papeUpdate2(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		
		pedmsct0002Service.papeUpdate2(map);
		pedmsct0002Service.historyInsert(map);
		
		return consultAdminSelect(map, model, searchParam);
	}
	
	//입점상담관리  상담결과 합격 업데이트
	@RequestMapping(value = "/edi/consult/PEDMCST0002updateCnsl1.do", method = RequestMethod.POST)
	public String cnslUpdate1(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		
		pedmsct0002Service.cnslUpdate1(map);
		
		return consultAdminSelect(map, model, searchParam);
	}
	
	//입점상담관리  상담결과 반려 업데이트 
	@RequestMapping(value = "/edi/consult/PEDMCST0002updateCnsl2.do", method = RequestMethod.POST)
	public String cnslUpdate2(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		pedmsct0002Service.cnslUpdate2(map);
		pedmsct0002Service.historyInsert(map);
		
		return consultAdminSelect(map, model, searchParam);
	}
	
	//입점상담관리  품평회 합격 업데이트
	@RequestMapping(value = "/edi/consult/PEDMCST0002updateEntshp1.do", method = RequestMethod.POST)
	public String entshpUpdate1(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		pedmsct0002Service.entshpUpdate1(map);
		pedmsct0002Service.historyInsert(map);

		return consultAdminSelect(map, model, searchParam);
	}
	
	//입점상담관리  품평회 불합격 업데이트 
	@RequestMapping(value = "/edi/consult/PEDMCST0002updateEntshp2.do", method = RequestMethod.POST)
	public String entshpUpdate2(@RequestParam Map<String,Object> map,  ModelMap model,SearchParam searchParam) throws Exception {
		
		pedmsct0002Service.entshpUpdate2(map);
		pedmsct0002Service.historyInsert(map);

		return consultAdminSelect(map, model, searchParam);
	}
	
	//입점상담관리 상세페이지
	@RequestMapping(value="/edi/consult/detailView.do", method = RequestMethod.GET)
    public String showConsultStep4Page( HttpServletRequest request, HttpServletResponse response,  Model model) throws Exception {
		
		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
		String businessNo = request.getParameter("businessNo");
		
		Vendor vendor = consultService.selectVendorInfo(businessNo);
		
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
		
		List<Sale> saleList = consultService.selectSaleInfoByVendor(businessNo);
		List<EdiCommonCode> otherStoreList = commService.selectOtherStoreList();
		List<VendorProduct> vendorProductList = consultService.selectVendorProduct(businessNo);
		
		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("otherStoreList", otherStoreList);
		model.addAttribute("vendor", vendor);
		model.addAttribute("attachFileFolder", attachFileFolder);
		
		model.addAttribute("conList", pedmsct0002Service.consultAdminSelectDetail(businessNo));
		model.addAttribute("conList_past", pedmsct0002Service.consultAdminSelectDetailPast(businessNo));
		
		return "edi/consult/detailView";
	}
    
	//입점상담관리 분류 AJAX
	@RequestMapping("/edi/consult/selectL1List")
    public ModelAndView getL1List(SearchParam searchParam,
    		HttpServletRequest request) {
		
		if(StringUtils.isEmpty(searchParam.getGroupCode())) {
			searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD_CON);
		}
		
		List<EdiCommonCode> resultL1List = pedmsct0002Service.selectL1List(searchParam);
		return AjaxJsonModelHelper.create(resultL1List);
	}
	
	//입점상담관리 분류 AJAX
		@RequestMapping("/edi/consult/selectL1ListApply")
	    public ModelAndView getL1ListApply(SearchParam searchParam,
	    		HttpServletRequest request) {
			
			if(StringUtils.isEmpty(searchParam.getGroupCode())) {
				searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD_CON);
			}
			
			List<EdiCommonCode> resultL1List = pedmsct0002Service.selectL1ListApply(searchParam);
			return AjaxJsonModelHelper.create(resultL1List);
		}
		
		
}
