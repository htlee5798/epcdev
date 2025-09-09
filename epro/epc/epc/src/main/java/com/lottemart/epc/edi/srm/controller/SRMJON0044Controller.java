package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMJON0044VO;
import com.lottemart.epc.edi.srm.service.SRMJON0044Service;
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
 * 입점상담 / 입점상담신청  / 입찰상담 정보등록(해외)
 *
 * @author LEE HYOUNG TAK
 * @since 2016.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.11.15  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0044Controller {

	@Autowired
	private SRMJON0044Service srmjon0044Service;

	/**
	 * 입찰상담 정보등록 [기본정보]
	 * @param HttpServletRequest
	 * @param Model
	 * @param SRMJON0040VO
	 * @return String
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/SRMJON0044.do")
	public String init(HttpServletRequest request, Model model, SRMJON0044VO vo) throws Exception {
		model.addAttribute("srmComp", srmjon0044Service.selectGlobalHiddenCompInfo(vo, request));
		return "/edi/srm/SRMJON0044";
	}


	/**
	 * 잠재업체 기본정보 체크
	 * @param SRMJON0044VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectGlobalHiddenCompInfoCheck.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectGlobalHiddenCompInfoCheck(@RequestBody SRMJON0044VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = srmjon0044Service.selectGlobalHiddenCompInfoCheck(vo, request);
		return resultMap;
	}


	/**
	 * 잠재업체 기본정보 등록
	 * @param HttpServletRequest
	 * @param Model
	 * @param SRMJON0044VO
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateGlobalHiddenCompInfo.do")
	public String updateGlobalHiddenCompInfo(HttpServletRequest request, Model model, SRMJON0044VO vo) throws Exception {
		model.addAttribute("result", srmjon0044Service.updateGlobalHiddenCompInfo(vo, request));
		model.addAttribute("srmComp", srmjon0044Service.selectGlobalHiddenCompInfo(vo, request));
		return "/edi/srm/SRMJON0044";
	}
}
