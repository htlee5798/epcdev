package com.lottemart.epc.edi.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.model.NEDMPAY0040VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0040Service;

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
public class NEDMPAY0040Controller {

	@Autowired
	private NEDMPAY0040Service NEDMPAY0040Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
		/**
		 * 기간별 결산정보  - > 세금계산서 정보
		 * @param locale
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/payment/NEDMPAY0040.do", method = RequestMethod.GET)
		public String credAgg(Locale locale,  ModelMap model,HttpServletRequest request) {
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
			
			return "/edi/payment/NEDMPAY0040";
		}
		
		/**
		 * 기간별 결산정보  - > 세금계산서 정보
		 * @param NEDMPAY0040VO
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/payment/NEDMPAY0040Select.json", method = RequestMethod.POST)
		public @ResponseBody Map<String, Object> selectCredAgg(@RequestBody NEDMPAY0040VO map,HttpServletRequest request) throws Exception {
			
			// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			map.setVenCds(epcLoginVO.getVendorId());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<NEDMPAY0040VO> paymentList = NEDMPAY0040Service.selectCredAggInfo(map);

			resultMap.put("paymentList", paymentList);

			
			return resultMap;
			
		}
		
		/**
		 * 기간별 결산정보  - > 세금계산서 정보 txt 파일 생성
		 * @param NEDMPAY0040VO
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/edi/payment/NEDMPAY0040Text.do", method = RequestMethod.POST)
		public void createTextCredAgg(NEDMPAY0040VO map,HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			map.setVenCds(epcLoginVO.getVendorId());
						
			NEDMPAY0040Service.createTextCredAgg(map,request,response);
			
		}
		
	
	
}
