package com.lottemart.epc.edi.buy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.lang.RuntimeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.buy.model.NEDMBUY0010VO;
import com.lottemart.epc.edi.buy.service.NEDMBUY0010Service;
import com.lottemart.epc.edi.commonUtils.commonUtil;

/**
 * @Class Name : NEDMBUY0010Controller
 * @Description : 매입정보 일자별 조회 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.16	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Controller
public class NEDMBUY0010Controller {

	@Autowired
	private NEDMBUY0010Service nedmbuy0010Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;


	@RequestMapping(value = "/edi/buy/NEDMBUY0010.do", method = RequestMethod.GET)
	public String day(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);

		map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
		map.put("srchEndDate", commonUtil.nowDateBack(nowDate));

		model.addAttribute("paramMap",map);

		return "/edi/buy/NEDMBUY0010";
	}

	/**
	 * 조회
	 * @param nEDMBUY0010VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/buy/NEDMBUY0010Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> newProductList(@RequestBody NEDMBUY0010VO nEDMBUY0010VO, HttpServletRequest request) throws Exception {


		if (nEDMBUY0010VO == null || request == null) {
			throw new IllegalArgumentException();
		}


		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMBUY0010VO.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		List<NEDMBUY0010VO>	resultList 	= 	nedmbuy0010Service.selectBuyInfo(nEDMBUY0010VO);


		resultMap.put("resultList", resultList);

		return resultMap;
	}

	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0010Text.do", method=RequestMethod.POST)
    public void createTextOrdProdList(NEDMBUY0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		nedmbuy0010Service.createTextOrdProdList(vo, request, response);
	}
}
