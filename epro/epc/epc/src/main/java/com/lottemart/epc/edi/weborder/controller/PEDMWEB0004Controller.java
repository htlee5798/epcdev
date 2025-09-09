package com.lottemart.epc.edi.weborder.controller;




import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0004Service;

/**
 * @Class Name : PEDMWEB0001Controller
 * @Description : 점포별 발주등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 06. 오후 1:33:50 ljy
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0004Controller extends BaseController{

	
	private PEDMWEB0004Service pEDMWEB0004Service;
	
	@Autowired
	public void setpEDMWEB0004Service(PEDMWEB0004Service pEDMWEB0004Service) {
		this.pEDMWEB0004Service = pEDMWEB0004Service;
	}
	
	/**
	 * Desc : 점포별 발주등록 화면
	 * @Method Name : orderTotList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0004")
    public String orderTotList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		if(StringUtils.isEmpty(searchParam.getWorkGb())){
			searchParam.setWorkGb("1");
		}
		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}
		if(StringUtils.isEmpty(searchParam.getRegStsfg())){
			searchParam.setRegStsfg("1");
		}
		if(StringUtils.isEmpty(searchParam.getMdModFg())){
			searchParam.setMdModFg("1");
		}
		
		String toDay = DateUtil.getToday("yyyy-MM-dd");
		searchParam.setOrdDy(toDay);
		
		model.addAttribute("paramMap",searchParam);
		
		return "edi/weborder/PEDMWEB0004";
	}
	
	/**
	 * Desc : 발주전체현황 조회
	 * @Method Name : getOrdTotInfo
	 * @param SearchWebOrder
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/tedOrdTotSelect.do")
	public ModelAndView getOrdTotInfo(@RequestBody SearchWebOrder vo) throws Exception
	{
		return AjaxJsonModelHelper.create(pEDMWEB0004Service.selectOrdTotList(vo));
	}
	
}
