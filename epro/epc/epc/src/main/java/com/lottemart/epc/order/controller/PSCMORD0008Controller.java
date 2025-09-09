package com.lottemart.epc.order.controller;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.order.model.PSCMORD0007VO;
import com.lottemart.epc.order.service.PSCMORD0008Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class PSCMORD0008Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMORD0008Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCMORD0008Service pscmord0008Service;

	//매출정보(상품상세별)
	@RequestMapping(value = "order/viewSaleDetailList.do", method = RequestMethod.GET)
	public String productDetail(Locale locale, ModelMap model, HttpServletRequest request) {

		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i] + ",";
		}
		ven = ven.substring(0, ven.length() - 1);
		map.put("ven", ven);

		map.put("startDate", commonUtil.nowDateBack(nowDate));
		map.put("endDate", commonUtil.nowDateBack(nowDate));
		model.addAttribute("resultMsg", messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault()));

		model.addAttribute("paramMap", map);

		return "order/PSCMORD0008";
	}

	//매출정보(상품상세별)
	@RequestMapping(value = "order/viewSaleDetailListSelect.do", method = RequestMethod.POST)
	public String selectProductDetail(@RequestParam Map<String, Object> map, ModelMap model, HttpServletRequest request) throws Exception {

		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if (nowHour >= 4 && nowHour <= 6) {
			return "order/PSCMORD0008";
		}

		String[] stors = map.get("storeVal").toString().split("-");
		if (!"".equals(stors[0])) {
			map.put("storeVal", stors);
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

		List<PSCMORD0007VO> list = null;
		try {
			list = pscmord0008Service.selectSaleProductDetailList(map);
			if (list == null || list.size() == 0) {
				model.addAttribute("resultMsg", messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("resultMsg", messageSource.getMessage("msg.common.fail.select", null, Locale.getDefault()));
		}

		model.addAttribute("paramMap", map);
		model.addAttribute("saleList", list);

		return "order/PSCMORD0008";
	}

	//매출정보(상품상세별) txt파일 생성
	@RequestMapping(value = "order/viewSaleDetailListText.do", method = RequestMethod.POST)
	public void createTextProductDetail(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String[] stors = map.get("storeVal").toString().split("-");
		if (!"".equals(stors[0])) {
			map.put("storeVal", stors);
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

		pscmord0008Service.createTextProductDetail(map, request, response);
	}

}
