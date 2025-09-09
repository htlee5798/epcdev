/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 4:27:11
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.epc.board.model.PSCPBRD0004SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0004Service;

/**
 * @Class Name : PSCPBRD0004Controller.java
 * @Description : 1:1문의 상세보기 Controller클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 4:27:06 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCPBRD0004Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0004Controller.class);
	@Autowired
	private PSCPBRD0004Service pscpbrd0004Service;
	
	/**
	 * Desc : 1:1문의 상세내용을 조회하는 메소드
	 * @Method Name : selectCounselPopupdetail
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectCounselPopupDetail.do")
	public String selectCounselPopupdetail(@ModelAttribute("searchVO") PSCPBRD0004SearchVO searchVO, Model model, HttpServletRequest request) throws Exception 
	{
		logger.debug("::: selectCounselPopupdetail :::");
		PSCPBRD0004SearchVO paramVO = pscpbrd0004Service.selectCounselPopupDetail(searchVO);
		model.addAttribute("counselDetail", paramVO);

		return "board/PSCPBRD0004";
	}
	
	/**
	 * Desc : 1:1문의상세보기에서 메모를 저장하는  메소드
	 * @Method Name : updateMemo
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/updateMemo.do")
	public String updateMemo(@ModelAttribute("searchVO") PSCPBRD0004SearchVO searchVO, Model model, HttpServletRequest request) throws Exception 
	{
		logger.debug("::: updateMemo :::");
		try
		{
			pscpbrd0004Service.updateMemo(searchVO);
			model.addAttribute("msg", "저장되었습니다.");
		}
		catch(Exception e)
		{
			model.addAttribute("msg", e.getMessage());
		}
		return "common/messageResult";
	}
	
	
}