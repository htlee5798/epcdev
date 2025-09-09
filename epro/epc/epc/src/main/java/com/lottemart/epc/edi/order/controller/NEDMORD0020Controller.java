package com.lottemart.epc.edi.order.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.order.model.NEDMORD0020VO;
import com.lottemart.epc.edi.order.service.NEDMORD0020Service;

/**
 * 발주정보 - > 기간정보 -> 전표별 Controller
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */
@Controller
public class NEDMORD0020Controller {

	@Autowired
	private NEDMORD0020Service nedmord0020Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 발주정보(전표별) 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0020.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String junpyo(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		String srchStartDate = request.getParameter("srchStartDate");
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All

		if(srchStartDate != null && !"".equals(srchStartDate)){
			//Defalut 날짜 입력(현재날짜)
			map.put("srchStartDate", srchStartDate);
			map.put("srchEndDate", srchStartDate);
		}else{
			//Defalut 날짜 입력(현재날짜)
			map.put("srchStartDate", nowDate);
			map.put("srchEndDate", nowDate);
		}


		model.addAttribute("paramMap",map);
		String returnPage = "/edi/order/NEDMORD0020";
		return returnPage;
	}

	/**
	 * 발주정보(전표별) txt파일 생성
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0020Text.do", method = RequestMethod.POST)
	public void createTextJunpyo(NEDMORD0020VO map,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());


		nedmord0020Service.createTextJunpyo(map,request,response);
	}



	/**
	 * 발주정보(전표별) 조회
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0020Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectJunpyo(@RequestBody NEDMORD0020VO map, HttpServletRequest request) throws Exception {


		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());


		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		List<NEDMORD0020VO> 	orderList 	= 	nedmord0020Service.selectJunpyoInfo(map);

		resultMap.put("orderList", orderList);
		return resultMap;
	}

}
