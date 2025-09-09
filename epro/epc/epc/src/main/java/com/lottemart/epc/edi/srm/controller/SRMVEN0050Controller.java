package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMVEN005001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0050VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0050Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * SRM 성과평가
 * 
 * @author LEE SANG GU
 * @since 2018.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2018.11.20  	 LEE SANG GU		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMVEN0050Controller {

	@Autowired
	private SRMVEN0050Service srmven0050Service;

	/**
	 * SRM 성과평가 화면
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/edi/ven/SRMVEN0050.do"})
	public String init() throws Exception {
		return "/edi/srm/SRMVEN0050";
	}
	
	/**
	 * SRM 성과평가 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSrmEvalRes.json", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectSrmEvalRes(@RequestBody SRMVEN0050VO vo, HttpServletRequest request) throws Exception {
		return srmven0050Service.selectSrmEvalRes(vo, request);
	}
	
	/**
	 * SRM 성과평가 상세팝업 조회
	 * @param SRMVEN0050VO
	 * @return String
	 * @throws Exception
	 */	
	@RequestMapping(value="/edi/ven/selectSrmEvalResDetailPopup", method = RequestMethod.POST)
	public String selectSrmEvalResDetailPopup(Model model, SRMVEN005001VO vo, HttpServletRequest request) throws Exception {				
		model.addAttribute("vo", vo);
		model.addAttribute("list", srmven0050Service.selectSrmEvalResDetailPopup(vo, request));		
		return "/edi/srm/SRMVEN005001";
	}
	

}
