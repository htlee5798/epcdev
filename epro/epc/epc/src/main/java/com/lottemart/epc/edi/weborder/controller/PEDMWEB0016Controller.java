package com.lottemart.epc.edi.weborder.controller;




import java.util.HashMap;
import java.util.Locale;
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
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0016Service;



/**
 * @Class Name : PEDMPRO0016Controller
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
public class PEDMWEB0016Controller extends BaseController{



	private PEDMWEB0016Service pEDMWEB0016Service;


	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0016Controller.class);

	@Autowired
	public void setpEDMWEB0016Service(PEDMWEB0016Service pEDMWEB0016Service) {
		this.pEDMWEB0016Service = pEDMWEB0016Service;
	}

	@Resource(name = "configurationService")
	private ConfigurationService config;



	/**
	 * Desc : 업체별 반품 전제 현황 이동
	 * @Method Name : VenOrdListAllView
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0016")
    public String VenOrdListAllView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);


		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB0016";
	}




	/**
	 * Desc : 업체별 반품 전제 현황 조회
	 * @Method Name : VenOrdListAllView
	 * @param SearchWebOrder
	 * @param HttpServletRequest
     * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB0016Select.do")
    public ModelAndView venderWebOrderRtnList(@RequestBody SearchWebOrder vo,HttpServletRequest request)throws Exception{

		Map<String, Object> rtnData = new HashMap<String, Object>();

		rtnData.put("state","-1");
		try{
			rtnData=pEDMWEB0016Service.selectVenRtnInfo(vo,request);
		}catch(Exception e){
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(rtnData);
	}


}
