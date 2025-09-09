package com.lottemart.epc.edi.imgagong.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.imgagong.model.NEDMIGG0010VO;
import com.lottemart.epc.edi.imgagong.service.NEDMIGG0110Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMIGG0110Controller
 * @Description : 임가공 입고 관리 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 * 수정일			수정자          		수정내용
 * ----------	-----------		---------------------------
 * 2018.11.22	SHIN SE JIN		최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Controller
public class NEDMIGG0110Controller {

	@Autowired
	private NEDMIGG0110Service NEDMIGG0110Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;

	
	/**
	 * 임가공 출고 관리 화면
	 * @param ModelMap, HttpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "/edi/imgagong/NEDMIGG0110.do", method = RequestMethod.GET)
	public String init(ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		map.put("repVendorId", epcLoginVO.getRepVendorId());	// 로그인 협력사 코드
		
		map.put("firstDate", commonUtil.firstDate(nowDate));	// 달 시작일자(01일)
		map.put("backDate", commonUtil.nowDateBack(nowDate));	// 당일이전일자
		map.put("nowDate", nowDate);							// 당일일자
		
		model.addAttribute("paramMap",map);
		
		return "/edi/imgagong/NEDMIGG0110";
	}
	
	/**
	 * 임가공 입고정보 삭제 처리
	 * @param NEDMIGG0010VO, HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/imgagong/imgagongGrDataDelete.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> imgagongGrDataDelete(@RequestBody NEDMIGG0010VO paramVO, HttpServletRequest request) throws Exception {
		return NEDMIGG0110Service.imgagongGrDataDelete(paramVO, request);
	}
	
}