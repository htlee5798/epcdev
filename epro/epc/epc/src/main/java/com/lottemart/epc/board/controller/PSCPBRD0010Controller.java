package com.lottemart.epc.board.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0010SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0010Service;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : PSCPBRD0010Controller.java
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
public class PSCPBRD0010Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0010Controller.class);
	
	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCPBRD0010Service pscpbrd0010Service;
	
	/**
	 * 콜센터 상세 내역을 조회
	 * @Description : 콜센터 상세 내역 초기 화면 로딩. 
	 * @Method Name : selectCallCenterPopupDetail
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/selectCallCenterPopupDetail.do")
	public String selectCallCenterPopupDetail(@ModelAttribute("searchVO") PSCPBRD0010SearchVO searchVO, Model model, HttpServletRequest request) throws Exception 
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
		
		PSCPBRD0010SearchVO paramVO =  pscpbrd0010Service.selectCallCenterPopupDetail(searchVO);
		
		DataMap map = new DataMap();
		map.put("majorCd", "QA001");
		List<DataMap> typeBigList = pscpbrd0010Service.selectCodeList(map);
		
		map.put("majorCd", "QA600");
		List<DataMap> typeSmallList = pscpbrd0010Service.selectCodeList(map);
		
		
		model.addAttribute("typeBigList", typeBigList);
		model.addAttribute("typeSmallList", typeSmallList);
		model.addAttribute("detail", paramVO);
		model.addAttribute("status", paramVO.getBoardPrgsStsCd());
		logger.debug("getBoardPrgsStsCd ==>" + paramVO.getBoardPrgsStsCd() + "<==");
		
		return "board/PSCPBRD0010";
	}
	
	/**
	 * 콜센터 상세 내역을 수정
	 * @Description : 콜센터 상세 내역을 수정
	 * @Method Name : updateCallCenterPopup
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/updateCallCenterPopup.do")
	public String updateCallCenterPopup(@ModelAttribute("searchVO") PSCPBRD0010SearchVO searchVO, Model model, HttpServletRequest request) throws Exception 
	{
		logger.debug("::: updateCallCenterDetail :::");
		try
		{
			pscpbrd0010Service.updateCallCenterPopupDetail(searchVO);
			model.addAttribute("msg", "저장되었습니다.");
		}
		catch(Exception e)
		{
			model.addAttribute("msg", e.getMessage());
		}
		
		return "common/messageResult";
	}
	
}
