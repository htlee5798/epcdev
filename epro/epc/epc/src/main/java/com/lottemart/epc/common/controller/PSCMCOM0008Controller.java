package com.lottemart.epc.common.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0007VO;
import com.lottemart.epc.common.model.PSCMCOM0008VO;
import com.lottemart.epc.common.service.PSCMCOM0007Service;
import com.lottemart.epc.common.service.PSCMCOM0008Service;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0010Service;
import com.lottemart.epc.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMCOM0008Controller {
	
	//임시보관함용 서비스
	private  PSCMCOM0008Service  pSCMCOM0008Service;

	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0008Controller.class);
	
	

	@Autowired
	public void setpSCMCOM0008Service(PSCMCOM0008Service pSCMCOM0008Service) {
		this.pSCMCOM0008Service = pSCMCOM0008Service;
	}

	


	@RequestMapping(value = "/common/PEDMCOM0008.do")
    public String VendCodeInfo(PSCMCOM0008VO vo, HttpServletRequest request ,Model model)throws Exception{

		return "common/PSCMCOM0008";
	}
	
	

	/**
	 * Desc : 업체코드
	 * @Method Name : tempNewProductList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/weborder/PEDMCOM0008Select.do")
    public ModelAndView VendCodeList(@RequestBody PSCMCOM0008VO vo, HttpServletRequest request)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("state","-1");
		try{
				result =	pSCMCOM0008Service.selectVendCodeList(vo,request);
				
		  }catch(Exception e){
			  
			    result.put("message",e.getMessage());
				logger.error(e.getMessage(), e);
				// TODO: handle exception
		  }
		
		
		
		return AjaxJsonModelHelper.create(result);
	}
	
	

	
}
