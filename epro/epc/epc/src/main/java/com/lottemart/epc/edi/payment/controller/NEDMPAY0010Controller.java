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
import com.lottemart.epc.edi.payment.model.NEDMPAY0010VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0010Service;

/**
 * 결산정보 - > 기간별 결산정보  - > 사업자 등록번호 Controller
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
public class NEDMPAY0010Controller {

	@Autowired
	private NEDMPAY0010Service NPEDMPAY0010Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 기간별 결산정보  - > 사업자 등록번호
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0010.do", method = RequestMethod.GET)
	public String cominfor(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		
		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return "/edi/payment/NEDMPAY0010";
	}
	
	/**
	 * 기간별 결산정보  - > 사업자 등록번호
	 * @param NEDMPAY0010VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/payment/NEDMPAY0010Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectCominfor(@RequestBody NEDMPAY0010VO map, HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMPAY0010VO> paymentList = NPEDMPAY0010Service.selectCominforInfo(map);

		resultMap.put("paymentList", paymentList);
		return resultMap;
	}
	
	
	
	
}
