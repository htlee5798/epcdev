/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 19. 오후 5:50:50
 * @author      : wcpark
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0002Service;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : PSCPBRD0002Controller
 * @Description : 공지사항 상세보기 Controller클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:51:01 wcpark
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCPBRD0002Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0002Controller.class);

	@Autowired
	private ConfigurationService config;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	@Resource(name="FileMngService")
	private FileMngService fileMngService;

	@Autowired
	private PSCPBRD0002Service pscpbrd0002Service;


	/**
	 * Desc : 공지사항 상세 내용을 조회하는 메소드
	 * @Method Name : selectDetailPopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectDetailPopup.do")
	public String selectDetailPopup(@ModelAttribute("searchvo") PSCPBRD0002SearchVO vo, Model model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인페이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null ) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		PSCPBRD0002SearchVO paramVO =  pscpbrd0002Service.selectDetailPopup(vo);

		List<FileVO> fileList = null;
		if(paramVO.getAtchFileId() != null && !"".equals(paramVO.getAtchFileId())) {
			FileVO fileVO = new FileVO();
			fileVO.setAtchFileId(paramVO.getAtchFileId());
			fileList = fileMngService.selectFileInfs(fileVO);
		}
		
		// HTML태그가 같이 보여져서 수정(2023.11.28)
		paramVO.setContent(StringEscapeUtils.unescapeHtml(paramVO.getContent()));
		
		model.addAttribute("detail", paramVO);
		model.addAttribute("fileList", fileList);
		return "board/PSCPBRD0002";
	}

}