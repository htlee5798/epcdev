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
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.sale.model.NEDMSAL0010VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0010Service;
/**
 * 매출정보 - > 기간별 매출정보  - > 일자별 Controller
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
public class NEDMSAL0010Controller {
	private static final Logger logger = LoggerFactory.getLogger(NEDMSAL0010Controller.class);

	@Autowired
	private NEDMSAL0010Service nedmsal0010Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	/**
	 * 매입정보 - > 매출정보(일자별)  
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0010.do", method = RequestMethod.GET)
	public String day(Locale locale,  ModelMap model,HttpServletRequest request) {
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
		ven = ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All
		
		//Defalut 날짜 입력(현재날짜)
		map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
		map.put("srchEndDate", commonUtil.nowDateBack(nowDate));
		
		model.addAttribute("paramMap",	map);
		model.addAttribute("nowHour",	nowHour);
		
		return "/edi/sale/NEDMSAL0010";
	}
	
	/**
	 * 매입정보 - > //매출정보(일자별) 조회
	 * @param NEDMSAL0010VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0010Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectDay(@RequestBody NEDMSAL0010VO vo, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Calendar cal = Calendar.getInstance();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);		
		if(nowHour >= 4 && nowHour <= 6 ){
			return resultMap;
		}
				
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		vo.setVenCds(epcLoginVO.getVendorId());
		//String[] tmpDate = vo.getSrchEndDate().split("-");
		//vo.setSrchStartDate(tmpDate[0]+"-"+tmpDate[1]+"-01");
		List<NEDMSAL0010VO> saleList = nedmsal0010Service.selectDayInfo(vo);

		resultMap.put("saleList", saleList);
		
		return resultMap;
	}
	
	/**
	 * 매입정보 - > //매출정보(일자별) txt파일 생성
	 * @param NEDMSAL0010VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0010Text.do", method = RequestMethod.POST)
	 public void createTextDay(NEDMSAL0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());
		
		//String[] tmpDate = vo.getSrchEndDate().split("-");
		//vo.setSrchStartDate(tmpDate[0]+"-"+tmpDate[1]+"-01");

		
		nedmsal0010Service.createTextDay(vo,request,response);
	}	
	/**
	 * 매입정보 - > //매출정보(일자별) count
	 * @param NEDMSAL0010VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/sale/NEDMSAL0010SelectCount.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> NEDMSAL0010SelectCount(@RequestBody NEDMSAL0010VO vo, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());
		
		String[] tmpDate = vo.getSrchEndDate().split("-");
		vo.setSrchStartDate(tmpDate[0]+"-"+tmpDate[1]+"-01");
		
		
		
		
		int cntSum = nedmsal0010Service.selectDayInfoCntSum(vo);
		
		logger.debug("leedb1121cntSum==>"+cntSum);
		logger.debug("leedb1121getSearchEntpCd==>"+vo.getSearchEntpCd());
		logger.debug("leedb1121getVenCds==>"+vo.getVenCds());
		
		
		logger.debug("leedb1121getSearchProductVal==>"+vo.getSearchProductVal());
		logger.debug("leedb1121getSearchStoreAl==>"+vo.getSearchStoreAl());
		
		resultMap.put("cntSum", cntSum);		
		return resultMap;
	}
}
