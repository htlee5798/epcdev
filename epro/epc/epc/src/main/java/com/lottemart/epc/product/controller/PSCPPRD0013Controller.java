/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.controller;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.product.model.PSCMPRD0011VO;
import com.lottemart.epc.product.service.PSCPPRD0013Service;

/**
 * @Class Name : PSCPPRD0013Controller
 * @Description : 상품이미지촬영스케쥴상세 조회 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:15:26 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCPPRD0013Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCPPRD0013Service pscpprd0013Service;

	/**
	 * Desc : 상품이미지촬영스케쥴상세 조회 메소드
	 * @Method Name : selectSchedulePopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectSchedulePopup.do")
	public String selectSchedulePopup(@ModelAttribute("vo") PSCMPRD0011VO vo, Model model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);
		
		PSCMPRD0011VO pscmprd0011VO = pscpprd0013Service.selectSchedulePopup(vo);
		pscmprd0011VO.setRservStartHour(pscmprd0011VO.getRservStartTm().substring(0, 2));
		pscmprd0011VO.setRservStartMin(pscmprd0011VO.getRservStartTm().substring(2, 4));
		pscmprd0011VO.setRservEndHour(pscmprd0011VO.getRservEndTm().substring(0, 2));
		pscmprd0011VO.setRservEndMin(pscmprd0011VO.getRservEndTm().substring(2, 4));
		model.addAttribute("pscmprd0011VO", pscmprd0011VO);
		return "product/PSCPPRD001301";
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 등록 화면 이동 메소드
	 * @Method Name : insertSchedulePopupView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/insertSchedulePopupView.do")
	public String insertSchedulePopupView(Model model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);
		
		String today = DateUtil.getToday("yyyy-MM-dd");
		model.addAttribute("today", today);
		
		return "product/PSCPPRD0013";
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 등록 메소드
	 * @Method Name : insertSchedulePopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/insertSchedulePopup.do")
	public String insertSchedulePopup(@ModelAttribute("vo") PSCMPRD0011VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		try {
			pscpprd0013Service.insertSchedulePopup(vo);
			model.addAttribute("msg", "저장되었습니다.");
		} catch(Exception e) {
			model.addAttribute("msg", e.getMessage());
		}
		return "common/messageResult";
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 수정 메소드
	 * @Method Name : updateSchedulePopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/updateSchedulePopup.do")
	public String updateSchedulePopup(@ModelAttribute("vo") PSCMPRD0011VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		try {
			pscpprd0013Service.updateSchedulePopup(vo);
			model.addAttribute("msg", "저장되었습니다.");
		} catch(Exception e) {
			model.addAttribute("msg", e.getMessage());
		}
		return "common/messageResult";
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 삭제 메소드
	 * @Method Name : deleteSchedule
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/deleteSchedulePopup.do")
	public String deleteSchedule(@ModelAttribute("vo") PSCMPRD0011VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		try {
			pscpprd0013Service.deleteSchedulePopup(vo);
			model.addAttribute("msg", "삭제되었습니다.");
		} catch(Exception e) {
			model.addAttribute("msg", e.getMessage());
		}
		return "common/messageResult";
	}
}
