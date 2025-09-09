package com.lottemart.epc.edi.order.controller;

import java.util.HashMap;
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

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.order.model.NEDMORD0040VO;
import com.lottemart.epc.edi.order.service.NEDMORD0040Service;

/**
 * 발주정보 - > 기간정보 - > 점포별 Controller
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
public class NEDMORD0040Controller {

	@Autowired
	private NEDMORD0040Service nedmord0040Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

		/**
		 * 발주정보(점포별) 첫페이지
		 * @param locale
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/order/NEDMORD0040.do", method = {RequestMethod.GET,RequestMethod.POST})
		public String store(Locale locale,  ModelMap model,HttpServletRequest request) {
			Map<String, String> map = new HashMap();

			String nowDate = DateUtil.getToday("yyyy-MM-dd");
			String startDate = request.getParameter("srchStartDate");

			// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

			String ven="";
			for(int i=0;i<epcLoginVO.getVendorId().length;i++){
				ven += epcLoginVO.getVendorId()[i]+",";
			}
			ven=ven.substring(0, ven.length()-1);
			map.put("ven", ven);	//session 협력업체코드 All

			if(startDate != null && !"".equals(startDate)){
				//Defalut 날짜 입력(현재날짜)
				map.put("srchStartDate", startDate);
				map.put("srchEndDate", startDate);
			}else{
				//Defalut 날짜 입력(현재날짜)
				map.put("srchStartDate", nowDate);
				map.put("srchEndDate", nowDate);
			}

			model.addAttribute("paramMap",map);
			String returnPage = "/edi/order/NEDMORD0040";
			return returnPage;
		}

	/**
	 * 발주정보(점포별) 조회
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0040Select.json", method = RequestMethod.POST)
	public String T_selectStore(ModelMap model,@RequestBody NEDMORD0040VO map, HttpServletRequest request) throws Exception {


		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());


		model.addAttribute("orderList", nedmord0040Service.selectStoreInfo(map));

		return "/edi/order/NEDMORD0040AJAX";
	}


	/**
	 * 발주정보(점포별) txt파일 생성
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0040Text.do", method = RequestMethod.POST)
	public void createTextStore(NEDMORD0040VO map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());


		nedmord0040Service.createTextStore(map,request,response);
	}
}
