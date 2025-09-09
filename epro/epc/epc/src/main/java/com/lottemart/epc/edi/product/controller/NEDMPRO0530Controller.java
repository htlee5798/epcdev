package com.lottemart.epc.edi.product.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0530VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0530Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : NEDMPRO0530Controller.java
 * @Description : 상품확장 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.07.31		yun				최초생성
 *               </pre>
 */
@Controller
public class NEDMPRO0530Controller extends BaseController {
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0530Service")
	private NEDMPRO0530Service nEDMPRO0530Service;
	
	/**
	 * 상품확장 Init
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0530.do")
	public String NEDMPRO0530Init(ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		
		model.addAttribute("today", DateUtil.getToday("yyyyMMdd"));	//오늘날짜
		
		return "edi/product/NEDMPRO0530";
	}
	
	/**
	 * 확장가능 상품내역 리스트 조회 jqGrid
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@ResponseBody 
	@RequestMapping(value = "/edi/product/selectExtAvailProdList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> selectExtAvailProdList(NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		if (paramVo == null || request == null) {
			throw new TopLevelException("");
		}
		
		return nEDMPRO0530Service.selectExtAvailProdList(paramVo, request);
	}
	
	/**
	 * 등록된 확장요청 정보 조회 (TAB 구성용)
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectExtTabList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<NEDMPRO0530VO> selectExtTabList(@RequestBody NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		if (paramVo == null || request == null) {
			throw new TopLevelException("");
		}
		return nEDMPRO0530Service.selectExtTabList(paramVo);
	}
	
	/**
	 * 채널확장 상세정보 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectTpcProdChanExtendDetailInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectTpcProdChanExtendDetailInfo(@RequestBody NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		if (paramVo == null || request == null) {
			throw new TopLevelException("");
		}
		return nEDMPRO0530Service.selectTpcProdChanExtendDetailInfo(paramVo);
	}
	
	/**
	 * 채널확장정보 등록
	 * @param paramVo
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateTpcProdChanExtendDetailInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> updateTpcProdChanExtendDetailInfo(@RequestBody NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		if (paramVo == null || request == null) {
			throw new TopLevelException("");
		}
		return nEDMPRO0530Service.updateTpcProdChanExtendDetailInfo(paramVo, request);
	}
	
	/**
	 * 채널확장 요청 전송 (Proxy 전송)
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertRequestProdChanExtendInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertRequestProdChanExtendInfo(@RequestBody NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		if (paramVo == null || request == null) {
			throw new TopLevelException("");
		}
		return nEDMPRO0530Service.insertRequestProdChanExtendInfo(paramVo, request);
	}
	
	/**
	 * 채널 확장 정보 삭제
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteTpcExtProdReg.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> deleteTpcExtProdReg(@RequestBody NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		if (paramVo == null || request == null) {
			throw new TopLevelException("");
		}
		return nEDMPRO0530Service.deleteTpcExtProdReg(paramVo, request);
	}
	
}
