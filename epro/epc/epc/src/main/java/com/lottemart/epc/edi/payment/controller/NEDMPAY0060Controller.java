package com.lottemart.epc.edi.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.lottemart.epc.edi.payment.model.NEDMPAY0060VO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0061VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0060Service;

/**
 * 결산정보 - > 기간별 결산정보  - >  판매장려금 정보 Controller
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
public class NEDMPAY0060Controller {

	@Autowired
	private NEDMPAY0060Service NEDMPAY0060Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	/**
	 * 기간별 결산정보  - > 구 판매장려금 정보
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0060.do", method = RequestMethod.GET)
	public String promoSale(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		
		model.addAttribute("paramMap",map);
		return "/edi/payment/NEDMPAY0060";
	}
	
	/**
	 * 기간별 결산정보  - > 구 판매장려금 정보
	 * @param NEDMPAY0060VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0060Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectPromoSale(@RequestBody NEDMPAY0060VO map, HttpServletRequest request) throws Exception {
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMPAY0060VO> paymentList = NEDMPAY0060Service.selectPromoSaleInfo(map);
		
		resultMap.put("paymentList", paymentList);
		
		return resultMap;
	}
	
	/**
	 * 기간별 결산정보  - > 신 판매장려금 정보
	 * @param NEDMPAY0061VO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0061.do", method = RequestMethod.GET)
	public String newPromoSale(Locale locale,  ModelMap model,HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		
		String nowDateYear = DateUtil.getToday("yyyy");
		String nowDateMonth = DateUtil.getToday("MM");
		map.put("startDate_year",  nowDateYear);
		map.put("startDate_month", nowDateMonth);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
	
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/NEDMPAY0061";
	}
	/**
	 * 기간별 결산정보  - > 신 판매장려금 정보
	 * @param NEDMPAY0061VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0061Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectNewPromoSale(@RequestBody NEDMPAY0061VO map,HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMPAY0061VO> paymentList = NEDMPAY0060Service.selectPromoNewSaleInfo(map);
		
		resultMap.put("paymentList", paymentList);
		
		return resultMap;
		
	}
	
	
}
