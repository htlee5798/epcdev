package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.service.SRMJON0043Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * 입점상담 / 입점상담신청  / 입찰상담 정보등록 [정보확인]
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.07  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0043Controller {

	@Autowired
	private SRMJON0043Service srmjon0043Service;

	/**
	 * 입찰상담 정보등록 [정보확인]
	 * @param Model
	 * @param SRMJON0043VO
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/SRMJON0043.do")
	public String init(Model model, SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(vo, request));
		return "/edi/srm/SRMJON0043";
	}

	/**
	 * 입점상담신청
	 * @param SRMJON0043VO
	 * @return HashMap<String, String>
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/updateHiddenCompReq.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> updateHiddenCompReq(@RequestBody SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = srmjon0043Service.updateHiddenCompReq(vo, request);
		return resultMap;
	}


	/**
	 * 입점상담신청 취소
	 * @param SRMJON0043VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateHiddenCompReqCancel.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> updateHiddenCompReqCancel(@RequestBody SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = srmjon0043Service.updateHiddenCompReqCancel(vo, request);
		return resultMap;
	}



}
