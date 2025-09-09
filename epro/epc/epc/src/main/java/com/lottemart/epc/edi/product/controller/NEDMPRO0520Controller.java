package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0520VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0028Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0520Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : NEDMPRO0520Controller.java
 * @Description : ESG 인증관리
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.28		yun				최초생성
 *               </pre>
 */
@Controller
public class NEDMPRO0520Controller extends BaseController{
	
private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0520Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0520Service")
	private NEDMPRO0520Service nEDMPRO0520Service;
	
	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	@Autowired
	NEDMPRO0028Service nEDMPRO0028Service;
	
	/**
	 * ESG 인증관리 init
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0520.do")
	public String NEDMPRO0520Init(ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", 		epcLoginVO);
		
		//ESG 첨부파일 확장자제한
		String fileExtLimitStr = config.getString("fileCheck.atchFile.ext.product.esg");
		model.addAttribute("extLimit", fileExtLimitStr);
		
		//ESG 대분류 기본 셋팅
		NEDMPRO0028VO pEsgVo = new NEDMPRO0028VO();
		List<NEDMPRO0028VO> esgMstL = nEDMPRO0028Service.selectEsgMstlList(pEsgVo);
		model.addAttribute("esgMstL", esgMstL);
		
		return "edi/product/NEDMPRO0520";
	}

	/**
	 * ESG 인증리스트 조회
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdEsgList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdEsgList(@RequestBody NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0520Service.selectProdEsgList(nEDMPRO0520VO, request);
	}
	
	/**
	 * ESG 인증정보 변경 (파일생성 별도)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateProdEsgInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> updateProdEsgInfo(@RequestBody NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0520Service.updateProdEsgInfo(nEDMPRO0520VO, request);
	}
	
	/**
	 * ESG 인증서첨부 (단건)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateProdEsgFileInfo.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateProdEsgFileInfo(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0520Service.updateProdEsgFileInfo(nEDMPRO0520VO, request);
	}
	
	
	/**
	 * ESG 인증정보 변경 (요청 시, 파일 생성)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateProdEsgInfoWithFile.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateProdEsgInfoWithFile(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0520Service.updateProdEsgInfoWithFile(nEDMPRO0520VO, request);
	}
	
	/**
	 * 대분류의 중분류 조회 (teamX)
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NselectNoTeamL2List.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> NselectNoTeamL2List(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.NselectNoTeamL2List(paramMap);
		returnMap.put("l2List", resultList);

		return returnMap;
	}
	
	/**
	 * 중분류의 소분류 조회 (teamX)
	 * @param paramMap
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectNgetNoTeamL3List.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectNgetNoTeamL3List(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectNgetNoTeamL3List(paramMap);
		returnMap.put("l3List", resultList);
		
		return returnMap;
	}
	
	/**
	 * ESG 인증정보 변경 요청(Proxy 전송)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateProdEsgSendProxy.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateProdEsgSendProxy(@RequestBody NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0520Service.updateProdEsgSendProxy(nEDMPRO0520VO, request);
	}
	
}
