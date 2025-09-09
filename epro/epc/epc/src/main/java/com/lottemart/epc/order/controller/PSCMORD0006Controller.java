package com.lottemart.epc.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.order.service.PSCMORD0006Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class PSCMORD0006Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(PSCMORD0006Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PSCMORD0006Service pscmord0006Service;

	@RequestMapping("order/salesInfobyStore.do")
	public String salesInfobyStore(ModelMap model, HttpServletRequest request) throws Exception{
	Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		request.setAttribute("epcLoginVO", epcLoginVO);

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);
		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		model.addAttribute("resultMsg", messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault()));

		model.addAttribute("paramMap",map);

		return "order/PSCMORD0006";
	}

	@RequestMapping("order/selectSalesInfobyStore.do")
	public String selectSalesInfobyStore(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception{

		//String[] tmpDate = map.get("endDate").toString().split("-");
		//map.put("startDate", tmpDate[0]+"-"+tmpDate[1]+"-01");

		String[] store = map.get("storeVal").toString().split("-");
		if(!"".equals(store[0])){
			map.put("storeVal", store);
		}

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		request.setAttribute("epcLoginVO", epcLoginVO);

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);

		if (map.get("entp_cd") == null || "".equals(map.get("entp_cd"))) {
			if (epcLoginVO.getCono().length > 1) {
				map.put("conos",epcLoginVO.getCono());
			} else {
				map.put("cono",epcLoginVO.getCono()[0]);
			}
		}

		List<DataMap> list = null;
		try {
			list = pscmord0006Service.selectSaleInfoByStore(map);
			if (list == null || list.size() == 0) {
				model.addAttribute("resultMsg", messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("resultMsg", messageSource.getMessage("msg.common.fail.select", null, Locale.getDefault()));
		}

		model.addAttribute("paramMap",map);
		model.addAttribute("saleList", list);

		return "order/PSCMORD0006";
	}

	@RequestMapping("order/selectSalesInfobyStoreExcel.do")
	public String selectSalesInfobyStoreExcel(@RequestParam Map<String, Object> map, ModelMap model, HttpServletRequest request) throws Exception {

		//String[] tmpDate = map.get("endDate").toString().split("-");
		//map.put("startDate", tmpDate[0] + "-" + tmpDate[1] + "-01");

		String[] store = map.get("storeVal").toString().split("-");
		if (!"".equals(store[0])) {
			map.put("storeVal", store);
		}

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		request.setAttribute("epcLoginVO", epcLoginVO);

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);

		if (map.get("entp_cd") == null || "".equals(map.get("entp_cd"))) {
			if (epcLoginVO.getCono().length > 1) {
				map.put("conos",epcLoginVO.getCono());
			} else {
				map.put("cono",epcLoginVO.getCono()[0]);
			}
		}

		List<DataMap> list = null;
		try {
			list = pscmord0006Service.selectSaleInfoByStore(map);
			if (list == null || list.size() == 0) {
				model.addAttribute("resultMsg", messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("resultMsg", messageSource.getMessage("msg.common.fail.select", null, Locale.getDefault()));
		}

		model.addAttribute("paramMap", map);
		model.addAttribute("saleList", list);

		return "order/PSCMORD000601";
	}

	@RequestMapping("order/selectSalesInfobyStoreText.do")
	public void selectSalesInfobyStoreText(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//String[] tmpDate = map.get("endDate").toString().split("-");
		//map.put("startDate", tmpDate[0] + "-" + tmpDate[1] + "-01");

		String[] store = map.get("storeVal").toString().split("-");
		if (!"".equals(store[0])) {
			map.put("storeVal", store);
		}

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);

		if (map.get("entp_cd") == null || "".equals(map.get("entp_cd"))) {
			if (epcLoginVO.getCono().length > 1) {
				map.put("conos",epcLoginVO.getCono());
			} else {
				map.put("cono",epcLoginVO.getCono()[0]);
			}
		}

		pscmord0006Service.createTextDay(map, request, response);
	}
}