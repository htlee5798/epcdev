package com.lottemart.epc.edi.weborder.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0220VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0220Service;


/**
 * @Class Name : NEDMWEB0220Controller
 * @Description : 발주전체현황 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0220Controller extends BaseController{



	private NEDMWEB0220Service nEDMWEB0220Service;

	@Autowired
	public void setnEDMWEB0220Service(NEDMWEB0220Service nEDMWEB0220Service) {
		this.nEDMWEB0220Service = nEDMWEB0220Service;
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
	@RequestMapping(value="/edi/weborder/NEDMWEB0220")
    public String VenOrdListAllView(NEDMWEB0220VO searchParam, HttpServletRequest request, Model model) {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);



		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/NEDMWEB0220";
	}



	/**
	 * Desc : 점포별 발주전제현황
	 * @Method Name : VenOrdList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/NEDMWEB0220Select.json")
    public ModelAndView VenOrdList(@RequestBody NEDMWEB0220VO vo,HttpServletRequest request)throws Exception{

		Map<String,Object> result = new HashMap<String,Object>();

		result.put("state", "-1");
		try{

			result = nEDMWEB0220Service.selectVenOrdAllInfo(vo,request);
			result.put("state", 	"0");
			result.put("message", 	"SUCCESS");

		}catch (Exception e) {
			// TODO: handle exception
			result.put("message",e.getMessage());
		}

		return AjaxJsonModelHelper.create(result);
	}


}
