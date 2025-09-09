package com.lottemart.epc.common.controller;

import java.util.List;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.service.PSCMCOM0003Service;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMCOM0003Controller {

	@Autowired
	private PSCMCOM0003Service PSCMCOM0003Service;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/common/selectDeliCodePopup.do")
	public String selectDeliCodePopup(ModelMap model) throws Exception {
		// 데이터 조회
		List<DataMap> list = PSCMCOM0003Service.selectDeliCodePopup();
		model.addAttribute("list", list);
		return "common/PSCMCOM0003";
	}
}
