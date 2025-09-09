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
import com.lottemart.epc.edi.weborder.model.NEDMWEB0230VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0230Service;


/**
 * 웹발주 - > MDer  - > 반품전체 현황   Controller
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
public class NEDMWEB0230Controller extends BaseController{


	@Autowired
	private NEDMWEB0230Service NEDMWEB0230Service;


	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0230Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;



	/**
	 * MDer  - > 반품등록 전체 현황   첫페이지
	 * @param NEDMWEB0230VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0230")
    public String VenOrdListAllView(NEDMWEB0230VO searchParam, HttpServletRequest request, Model model) {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);


		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/NEDMWEB0230";
	}




	/**
	 * MDer  - > 반품등록 전체 현황
	 * @param NEDMWEB0230VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/weborder/NEDMWEB0230Select.do")
    public ModelAndView venderWebOrderRtnList(@RequestBody NEDMWEB0230VO vo,HttpServletRequest request)throws Exception{

		Map<String, Object> rtnData = new HashMap<String, Object>();

		rtnData.put("state","-1");
		try{
			rtnData=NEDMWEB0230Service.selectVenRtnInfo(vo,request);
		}catch(Exception e){
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(rtnData);
	}


}
