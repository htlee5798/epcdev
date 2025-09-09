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

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.buy.model.NEDMBUY0050VO;
import com.lottemart.epc.edi.buy.service.NEDMBUY0050Service;
import com.lottemart.epc.edi.commonUtils.commonUtil;

/**
 * @Class Name : NEDMBUY0050Controller
 * @Description : 매입정보 전표상세별 조회 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.17	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Controller
public class NEDMBUY0050Controller {

	@Autowired
	private NEDMBUY0050Service nedmbuy0050Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;


	@RequestMapping(value = "/edi/buy/NEDMBUY0050.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String day(Locale locale,  ModelMap model,HttpServletRequest request, NEDMBUY0050VO nEDMBUY0050VO) {
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


		if(nEDMBUY0050VO.getIsForwarding() != null && "Y".equals(nEDMBUY0050VO.getIsForwarding())){
			map.put("isForwarding",  nEDMBUY0050VO.getIsForwarding());
			map.put("srchStartDate", nEDMBUY0050VO.getSrchStartDate());
			map.put("srchEndDate",   nEDMBUY0050VO.getSrchEndDate());
		}else{
			map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
			map.put("srchEndDate",   commonUtil.nowDateBack(nowDate));
		}

		map.put("srchEntpCode", nEDMBUY0050VO.getSrchEntpCode()  );
		map.put("srchBuying",   nEDMBUY0050VO.getSrchBuying() );

		model.addAttribute("paramMap",map);

		return "/edi/buy/NEDMBUY0050";
	}


	@RequestMapping(value="/edi/buy/NEDMBUY0050Select.json", method=RequestMethod.POST)
	public String newProductList(ModelMap model, @RequestBody NEDMBUY0050VO nEDMBUY0050VO, HttpServletRequest request) throws Exception {


		if (nEDMBUY0050VO == null || request == null) {
			throw new IllegalArgumentException();
		}


		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMBUY0050VO.setVenCds(epcLoginVO.getVendorId());

		List<NEDMBUY0050VO>	resultList 	= 	nedmbuy0050Service.selectJunpyoDetailInfo(nEDMBUY0050VO);

		model.addAttribute("resultList", resultList);

		return "/edi/buy/NEDMBUY0050_AJAX";
	}

	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0050Text.do", method=RequestMethod.POST)
    public void createTextOrdProdList(NEDMBUY0050VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		nedmbuy0050Service.createTextOrdProdList(vo, request, response);
	}

}
