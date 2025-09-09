package com.lottemart.epc.board.controller;


import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.epc.board.model.PSCPBRD0009SaveVO;
import com.lottemart.epc.board.service.PSCPBRD0009Service;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : PSCPBRD0009Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCPBRD0009Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0009Controller.class);
	
	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCPBRD0009Service pscpbrd0009Service;
	
	/**
	 * 콜센터 등록 폼
	 * @Description : 콜센터 등록 초기 화면 로딩. 
	 * @Method Name : insertCallCenterPopupForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertCallCenterPopupForm.do")
	public String insertCallCenterPopupForm(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		request.setAttribute("epcLoginVO", epcLoginVO);
		
		return "board/PSCPBRD0009";
	}
	
	/**
	 * 콜센터 내역을 등록
	 * @Description : 콜센터 내역을 등록
	 * @Method Name : insertCallCenterPopup
	 * @param VO
	 * @param Model
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertCallCenterPopup.do")
	public String insertCallCenterPopup(@ModelAttribute("saveVO") PSCPBRD0009SaveVO saveVO, Model model) throws Exception 
	{
		try
		{
			pscpbrd0009Service.insertCallCenterPopup(saveVO);
			model.addAttribute("msg", "저장되었습니다.");
		} 
		catch(Exception e) 
		{
			model.addAttribute("msg", e.getMessage());
		}
		
		return "common/messageResult";
	}
	
}
