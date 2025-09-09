package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0140VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0140Service;



@Controller
public class NEDMPRO0140Controller {

	@Autowired
	private NEDMPRO0140Service service;

	@Resource(name = "configurationService")
	private ConfigurationService config;



	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0140.do")
	public String init(ModelMap model, HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		String nowDate =DateUtil.getToday("yyyy-MM-dd");


		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven = ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All

		//Defalut 날짜 입력(현재날짜)
		//model.addAttribute("srchEndDt",	DateUtil.formatDate(DateUtil.addDay(nowDate, -7), "-"));		//검색종료일
		map.put("srchStartDate", DateUtil.formatDate(DateUtil.addDay(nowDate, -6), "-"));

		//map.put("srchEndDate", nowDate);

		model.addAttribute("paramMap",map);

		return "/edi/product/NEDMPRO0140";
	}


	@RequestMapping(value = "/edi/product/NEDMPRO0140.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> NEDMPRO0140(@RequestBody NEDMPRO0140VO vo, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMPRO0140VO> plcProductList = service.selectPlcProductList(vo);

		//resultMap.put("orderList", orderList);
		resultMap.put("plcProductList", plcProductList);
		return resultMap;
	}




}
