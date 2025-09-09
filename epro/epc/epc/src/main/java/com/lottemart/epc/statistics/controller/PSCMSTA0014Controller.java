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

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.statistics.model.PSCMSTA0014VO;
import com.lottemart.epc.statistics.service.PSCMSTA0014Service;

@Controller
public class PSCMSTA0014Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMSTA0014Service pscmsta0014Service;

	@RequestMapping(value ="/statistics/rentCarPickUp.do")
	public String rentCarPickUp(@ModelAttribute("searchVO") PSCMSTA0014VO searchVO, HttpServletRequest request, ModelMap model ) throws Exception {

		// 주문일자 조회조건이 없을 경우
		searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
		searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));

		/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		String extStrCd = request.getParameter("extStrCd");
		model.addAttribute("extStrCd", extStrCd);

		List<DataMap> martStrList = pscmsta0014Service.selectMartStrList();
		model.addAttribute("martStrList", martStrList);

		List<DataMap> superStrList = pscmsta0014Service.selectSuperStrList(extStrCd);
		model.addAttribute("superStrList", superStrList);

		List<DataMap> pickupStsList = pscmsta0014Service.selectPickupStsList();
		model.addAttribute("pickupStsList", pickupStsList);

		List<DataMap> deliStatusList = pscmsta0014Service.selectDeliStatusList();
		model.addAttribute("deliStatusList", deliStatusList);

		List<DataMap> OrdDivnList = pscmsta0014Service.selectOrdDivnList();
		model.addAttribute("OrdDivnList", OrdDivnList);

		model.addAttribute("paginationInfo", paginationInfo);


		return "statistics/PSCMSTA0014";
	}

	@RequestMapping(value ="/statistics/selectRentCarPickUp.do")
	public String selectCrossPickUpList(@ModelAttribute("searchVO") PSCMSTA0014VO searchVO, HttpServletRequest request ,ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		request.setAttribute("mSelStr" , searchVO.getMartStrNo());
		request.setAttribute("sSelStr" , searchVO.getsuperStrNo());
		request.setAttribute("pSelStr" , searchVO.getPickupStatus());
		request.setAttribute("dSelStr" , searchVO.getDeliStatus());
		request.setAttribute("selDiv"  , searchVO.getOrdDivn());
		request.setAttribute("selPtime", searchVO.getPickUpTime());
		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		/** paging setting */
		String rowsPerPage = StringUtil.null2str(searchVO.getRowsPerPage(), config.getString("count.row.per.page"));
		int startRow = ((searchVO.getPageIndex()-1)*Integer.parseInt(rowsPerPage))+1;
		int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(startRow);
		searchVO.setLastIndex(endRow);
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		String extStrCd = request.getParameter("extStrCd");
		model.addAttribute("extStrCd", extStrCd);

		List<DataMap> martStrList = pscmsta0014Service.selectMartStrList();
		model.addAttribute("martStrList", martStrList);

		List<DataMap> superStrList = pscmsta0014Service.selectSuperStrList(extStrCd);
		model.addAttribute("superStrList", superStrList);

		List<DataMap> pickupStsList = pscmsta0014Service.selectPickupStsList();
		model.addAttribute("pickupStsList", pickupStsList);

		List<DataMap> deliStatusList = pscmsta0014Service.selectDeliStatusList();
		model.addAttribute("deliStatusList", deliStatusList);

		List<DataMap> OrdDivnList = pscmsta0014Service.selectOrdDivnList();
		model.addAttribute("OrdDivnList", OrdDivnList);

		List<DataMap> list = pscmsta0014Service.selectRentCarPickUp(searchVO);
		model.addAttribute("list", list);

		  int totalCount = 0;
	        String resultMsg = "해당 자료가 없습니다.";
	        if(list.size() > 0) {
	        	resultMsg = "정상적으로 조회되었습니다.";
	        	totalCount = list.get(0).getInt("TOTAL_COUNT");
	        }

	        model.addAttribute("resultMsg", resultMsg);
	        request.setAttribute("ordNo", searchVO.getOrdNo());
	        request.setAttribute("resultYn", "Y");
	        request.setAttribute("totalCount", totalCount);
	        request.setAttribute("rowsPerPage",rowsPerPage);
	        request.setAttribute("currentPage",searchVO.getPageIndex());

		return "statistics/PSCMSTA0014";
	}

	@RequestMapping(value ="/statistics/rentCarPickUpListExcel.do")
	  public String rentCarPickUpListExcel(@ModelAttribute("searchVO") PSCMSTA0014VO searchVO, HttpServletRequest request ,ModelMap model) throws Exception {

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			searchVO.setStartDate(DateUtil.getToday("yyyy-MM")+"-01");
			searchVO.setEndDate(DateUtil.getToday("yyyy-MM-dd"));
		}

		String rowsPerPage = StringUtil.null2str(searchVO.getRowsPerPage(), config.getString("count.row.per.page"));
		int startRow = ((searchVO.getPageIndex()-1)*Integer.parseInt(rowsPerPage))+1;
		int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

		searchVO.setFirstIndex(startRow);
		searchVO.setLastIndex(65000);

		List<DataMap> list = pscmsta0014Service.selectRentCarPickUp(searchVO);
		model.addAttribute("list", list);

		return "statistics/PSCMSTA001401";
	}

	@RequestMapping(value ="/statistics/saveRentCarPickUp.do")
	  public ModelAndView saveRentCarPickUp(HttpServletRequest request ,ModelMap model) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
	    try{
			String chkBox = (String)request.getParameter("chk_box_arr");
			String pFlag = (String)request.getParameter("pickup_Sts");

		    String[] chkBoxArr = chkBox.split(",");
		    for(int i=0; i<chkBoxArr.length; i++){
		    	String orderId = chkBoxArr[i];
		    	if(orderId.equals("") || orderId == null){
		    		throw new IllegalArgumentException("처리중 에러가 발생하였습니다.");
		    	}else{
		    		paramMap.put("orderId", orderId);
		    		paramMap.put("pickupStatus", pFlag);
		    		paramMap.put("adminId", epcLoginVO.getAdminId());
		    		pscmsta0014Service.insertPickupStatus(paramMap);

		    		resultMap.put("rstMsg", "저장되었습니다.");
		    	}
		    }
	    }catch (Exception e) {
	    	resultMap.put("rstMsg","처리중 오류가 발생하였습니다.");
	    }
	    return AjaxJsonModelHelper.create(resultMap);
	}

	@RequestMapping(value ="/statistics/pickUpListExcel.do")
	  public String pickUpListExcel(@ModelAttribute("searchVO") PSCMSTA0014VO searchVO, HttpServletRequest request ,ModelMap model) throws Exception {

		DataMap paramMap = new DataMap();

		String chkBox = (String)request.getParameter("chk_box_arr");
		String extStrCd = request.getParameter("extStrCd");

		String[] chkBoxArr = chkBox.split(",");
		paramMap.put("chkBoxArr",chkBoxArr);
		paramMap.put("extStrCd",extStrCd);
		List<DataMap> list = pscmsta0014Service.pickUpListExcel(paramMap);
		model.addAttribute("list", list);
		return "statistics/PSCMSTA001402";
	}

	@RequestMapping(value ="/statistics/excgOrRtnExcel.do")
	public String excgOrRtnExcel() throws Exception {
		return "statistics/PSCMSTA001403";
	}
}
