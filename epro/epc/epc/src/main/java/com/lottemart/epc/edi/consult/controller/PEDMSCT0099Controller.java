package com.lottemart.epc.edi.consult.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.model.PEDMSCT0099VO;
import com.lottemart.epc.edi.consult.service.PEDMSCT0099Service;

@Controller
public class PEDMSCT0099Controller {
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private PEDMSCT0099Service defService;

	// 우편번호 관리 Init
	@RequestMapping(value = "/edi/consult/PEDMCST0099.do", method = RequestMethod.GET)
	public String zipInit() {
		return "/edi/consult/PEDMCST0099";
	}
	
	/**
	 * 사업자 List 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/consult/selectPEDMCST0099.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectPEDMCST0099(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		
		List<PEDMSCT0099VO>	resultList = defService.selectBmanNoZipList(epcLoginVO);
		
		resultMap.put("resultList", resultList);
		
		return resultMap;
	}
	
	/**
	 * 주소 찾기 Init
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/PEDMCST009901.do", method = RequestMethod.GET)
	public String zipCdInit() {
		return "/edi/consult/PEDMCST009901";
	}
	
	/**
	 * 주소 검색
	 * @param paramVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/consult/selectZipList.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectZipList(@RequestBody PEDMSCT0099VO paramVO, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		
		List<PEDMSCT0099VO>	resultList = defService.selectZipList(paramVO);
		
		resultMap.put("resultList", resultList);
		
		return resultMap;
	}

	/**
	 * 주소 저장
	 * @param paramVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/consult/saveZip.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveZip(@RequestBody PEDMSCT0099VO paramVO, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		
		defService.saveZip(paramVO);
		
		resultMap.put("msgCd", "0");
		
		return resultMap;
	}
    
}
