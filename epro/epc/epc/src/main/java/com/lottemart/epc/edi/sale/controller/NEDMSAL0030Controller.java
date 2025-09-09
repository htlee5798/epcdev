package com.lottemart.epc.edi.sale.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.sale.model.NEDMSAL0030VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0030Service;
/**
 * 매출정보 - > 기간별 매출정보  - > 상품별 Controller
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
public class NEDMSAL0030Controller {

	@Autowired
	private NEDMSAL0030Service nedmsa0030Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 매출정보 - > 매출정보(상품별)  
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0030.do", method = {RequestMethod.GET , RequestMethod.POST})
	public String product(Locale locale,  ModelMap model,HttpServletRequest request) {
		String srchEndDate = StringUtils.trimToEmpty(request.getParameter("endDate"));
		String srchStartDate = StringUtils.trimToEmpty(request.getParameter("startDate"));
		Map<String, String> map = new HashMap();
		
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		if(srchEndDate != null && !"".equals(srchEndDate)){
			map.put("srchStartDate", srchStartDate);
			map.put("srchEndDate", srchEndDate);
		}else{
			map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
			map.put("srchEndDate", commonUtil.nowDateBack(nowDate));
		}
		
		map.put("entp_cd", request.getParameter("searchEntpCd") );
				
		model.addAttribute("paramMap",map);
		model.addAttribute("nowHour",nowHour);
		
		return "/edi/sale/NEDMSAL0030";
	}
	
	/**
	 * 매출정보 - > 매출정보(상품별)  
	 * @param NEDMSAL0030VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0030Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectProduct(@RequestBody NEDMSAL0030VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		Calendar cal = Calendar.getInstance();
		/*int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		if(nowHour >= 4 && nowHour <= 6 ){
			return resultMap;
		}*/
				
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());
		

		List<NEDMSAL0030VO> saleList = nedmsa0030Service.selectProductInfo(vo);
		
		resultMap.put("saleList", saleList);
		
		return resultMap;
	}
	
	/**
	 * 매출정보 - > 매출정보(상품별)   txt파일 생성 
	 * @param NEDMSAL0030VO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0030Text.do", method = RequestMethod.POST)
	public void createTextProduct(NEDMSAL0030VO map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());
		
		
		nedmsa0030Service.createTextProduct(map,request,response);
	}
	
}
