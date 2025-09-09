package com.lottemart.epc.statistics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.RuntimeException;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.statistics.model.PSCMSTA0013VO;
import com.lottemart.epc.statistics.service.PSCMSTA0013Service;

@Controller
public class PSCMSTA0013Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMSTA0013Service pscmsta0013Service;

	@RequestMapping(value ="/statistics/crossPickUpList.do")
	  public String crossPickUp(@ModelAttribute("searchVO") PSCMSTA0013VO searchVO, HttpServletRequest request, ModelMap model ) throws Exception {

		// 주문일자 조회조건이 없을 경우
		searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
		searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));

		/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		List<DataMap> martStrList = pscmsta0013Service.selectMartStrList();
		model.addAttribute("martStrList", martStrList);

		List<DataMap> superStrList = pscmsta0013Service.selectSuperStrList();
		model.addAttribute("superStrList", superStrList);

		List<DataMap> pickupStsList = pscmsta0013Service.selectPickupStsList();
		model.addAttribute("pickupStsList", pickupStsList);

		List<DataMap> deliStatusList = pscmsta0013Service.selectDeliStatusList();
		model.addAttribute("deliStatusList", deliStatusList);

		model.addAttribute("paginationInfo", paginationInfo);

		return "statistics/PSCMSTA0013";
	}

	@RequestMapping(value ="/statistics/selectCrossPickUpList.do")
	  public String selectCrossPickUpList(@ModelAttribute("searchVO") PSCMSTA0013VO searchVO,HttpServletRequest request ,ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		request.setAttribute("mSelStr" , searchVO.getMartStrNo());
		request.setAttribute("sSelStr" , searchVO.getsuperStrNo());
		request.setAttribute("pSelStr" , searchVO.getPickupStatus());
		request.setAttribute("dSelStr" , searchVO.getDeliStatus());
		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<DataMap> martStrList = pscmsta0013Service.selectMartStrList();
		model.addAttribute("martStrList", martStrList);

		List<DataMap> superStrList = pscmsta0013Service.selectSuperStrList();
		model.addAttribute("superStrList", superStrList);

		List<DataMap> pickupStsList = pscmsta0013Service.selectPickupStsList();
		model.addAttribute("pickupStsList", pickupStsList);

		List<DataMap> deliStatusList = pscmsta0013Service.selectDeliStatusList();
		model.addAttribute("deliStatusList", deliStatusList);

		List<DataMap> list = pscmsta0013Service.selectCrossPicUpList(searchVO);
		model.addAttribute("list", list);

		  int totalCount = 0;
	        String resultMsg = "해당 자료가 없습니다.";
	        if(list.size() > 0) {
	        	resultMsg = "정상적으로 조회되었습니다.";
	        	totalCount = list.get(0).getInt("TOTAL_COUNT");
	        }
	        paginationInfo.setTotalRecordCount(totalCount);
	        model.addAttribute("paginationInfo", paginationInfo);

	        model.addAttribute("resultMsg", resultMsg);

		return "statistics/PSCMSTA0013";
	}

	@RequestMapping(value ="/statistics/crossPickUpListExcel.do")
	  public String crossPickUpListExcel(@ModelAttribute("searchVO") PSCMSTA0013VO searchVO,HttpServletRequest request ,ModelMap model) throws Exception {

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		List<DataMap> list = pscmsta0013Service.selectCrossPicUpListExcel(searchVO);
		model.addAttribute("list", list);

		return "statistics/PSCMSTA001301";
	}

	@RequestMapping(value ="/statistics/crossPickUpSave.do")
	  public ModelAndView crossPickUpSave(HttpServletRequest request ,ModelMap model) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
	    try{
			String chkBox = (String)request.getParameter("chk_box_arr");
			String pickupSts = (String)request.getParameter("pickup_Sts_arr");
		    String[] chkBoxArr = chkBox.split(",");
		    String[] pickupStsArr = pickupSts.split(",");
		    for(int i=0; i<chkBoxArr.length; i++){
		    	String orderId = chkBoxArr[i];
		    	String pickupStatus = pickupStsArr[i];
		    	if(orderId.equals("") || orderId == null || pickupStatus.equals("") || pickupStatus == null){
		    		throw new IllegalArgumentException("처리중 에러가 발생하였습니다.");
		    	}else{
		    		paramMap.put("orderId", orderId);
		    		paramMap.put("pickupStatus", pickupStatus);
		    		paramMap.put("adminId", epcLoginVO.getAdminId());
		    		pscmsta0013Service.insertPickupStatus(paramMap);

		    		resultMap.put("rstMsg", "저장되었습니다.");
		    	}
		    }
	    }catch (Exception e) {
	    	resultMap.put("rstMsg","처리중 오류가 발생하였습니다.");
	    }
	    return AjaxJsonModelHelper.create(resultMap);
	}

}
