package com.lottemart.epc.product.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.service.PSCPPRD0007Service;

/**
 * @Class Name : PSCPPRD0007Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCPPRD0007Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0007Controller.class);

	@Autowired
	private PSCPPRD0007Service pscpprd0007Service;

	/**
	 * 원산지정보 목록(팝업)
	 * @Description : 원산지정보 목록을 얻어서 초기페이지 로딩시 그리드에 리턴 
	 * @Method Name : selectLocationPopUpView
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectLocationPopUpView.do")
	public String selectLocationPopUpView(HttpServletRequest request) throws Exception 
	{
		List<DataMap> codeList = pscpprd0007Service.selectLocationList();
		logger.debug("selectLocationList size ==>" + codeList.size() + "<==");
		request.setAttribute("codeList", codeList);

		return "product/internet/PSCPPRD0007";
	}

}
