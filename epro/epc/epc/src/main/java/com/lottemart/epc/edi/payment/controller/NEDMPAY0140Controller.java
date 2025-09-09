package com.lottemart.epc.edi.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0020VO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0140VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0140Service;
import com.lottemart.epc.edi.payment.service.PEDMPAY0020Service;

/**
 * 결산정보 - > 거래실적조회  - > 패밀리론 Controller
 * 
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
public class NEDMPAY0140Controller {

	@Autowired
	private NEDMPAY0140Service nedmpay0140Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 거래실적조회  - > 패밀리론
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
		@RequestMapping(value = "/edi/payment/NEDMPAY0140.do", method = RequestMethod.GET)
		public String familyLoan(Locale locale,  ModelMap model,HttpServletRequest request) {
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
			
			map.put("srchStartDate", nowDate);
			map.put("srchEndDate", nowDate);
			
			model.addAttribute("paramMap",map);
			
			return "/edi/payment/NEDMPAY0140";
		}
		
		/**
		 * 거래실적조회  - > 패밀리론
		 * @param NEDMPAY0140VO
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/payment/NEDMPAY0140Select.json", method = RequestMethod.POST)
		public @ResponseBody Map<String, Object> selectFamilyLoan(@RequestBody NEDMPAY0140VO map, HttpServletRequest request) throws Exception {
			// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			map.setVenCds(epcLoginVO.getVendorId());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<NEDMPAY0140VO> paymentList = nedmpay0140Service.selectFamilyLoan(map);

			resultMap.put("paymentList", paymentList);
			return resultMap;
			
		}
		
		/**
		 * 거래실적조회  - > 패밀리론 txt파일생성
		 * @param NEDMPAY0140VO
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/edi/payment/NEDMPAY0140Text.do", method = RequestMethod.POST)
		public void createTextFamilyLoan(NEDMPAY0140VO map,HttpServletRequest request, HttpServletResponse response) throws Exception {
			// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			map.setVenCds(epcLoginVO.getVendorId());
			
			nedmpay0140Service.createTextFamilyLoan(map,request,response);
			
		}
	
}
