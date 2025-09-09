package com.lottemart.epc.edi.weborder.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;



import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0011Service;


/**
 * @Class Name : PEDMPRO0003Controller
 * @Description : 임시보관함 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 05. 오후 1:33:50 kks
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0011Controller extends BaseController{



	private PEDMWEB0011Service pEDMWEB0011Service;

	@Autowired
	public void setpEDMWEB0011Service(PEDMWEB0011Service pEDMWEB0011Service) {
		this.pEDMWEB0011Service = pEDMWEB0011Service;
	}


	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * Desc : 점포별 발주전제현황 이동
	 * @Method Name : VenOrdListAllView
	 * @param searchParam
	 * @param HttpServletRequest
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0011")
    public String VenOrdListAllView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);



		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB0011";
	}



	/**
	 * Desc : 점포별 발주전제현황
	 * @Method Name : VenOrdList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB0011Select.do")
    public ModelAndView VenOrdList(@RequestBody SearchWebOrder vo,HttpServletRequest request)throws Exception{

		Map<String,Object> result = new HashMap<String,Object>();

		result.put("state", "-1");
		try{

			result = pEDMWEB0011Service.selectVenOrdAllInfo(vo,request);
			result.put("state", 	"0");
			result.put("message", 	"SUCCESS");

		}catch (Exception e) {
			// TODO: handle exception
			result.put("message",e.getMessage());
		}

		return AjaxJsonModelHelper.create(result);
	}


}
