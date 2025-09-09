package com.lottemart.epc.edi.comm.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.RFCCommonVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;

@Controller
public class RFCCommonController {

	@Autowired
	private RFCCommonService rfcCommonService;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * RFC Call
	 * @param paramVO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/comm/rfcCall.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> rfcCall(@RequestBody RFCCommonVO paramVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		return rfcCommonService.rfcCall(paramVO.getProxyNm(), paramVO.getParam(),epcLoginVO.getAdminId());
	}


}
