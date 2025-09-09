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
import com.lottemart.epc.edi.sale.model.NEDMSAL0020VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0020Service;
/**
 * 매출정보 - > 기간별 매출정보  - > 점포별 Controller
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
public class NEDMSAL0020Controller {

	@Autowired
	private NEDMSAL0020Service nedmsa00020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
		/**
		 * 매출정보 - > 매출정보(점포별)  
		 * @param locale
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/sale/NEDMSAL0020.do", method = {RequestMethod.GET , RequestMethod.POST})
		public String store(Locale locale,  ModelMap model,HttpServletRequest request) {
			String srchEndDate = StringUtils.trimToEmpty(request.getParameter("endDate"));
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
				map.put("srchStartDate", srchEndDate);
				map.put("srchEndDate", srchEndDate);
			}else{
				map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
				map.put("srchEndDate", commonUtil.nowDateBack(nowDate));
			}
			
			map.put("entp_cd", request.getParameter("searchEntpCd") );
			model.addAttribute("paramMap",map);
			model.addAttribute("nowHour",nowHour);
			
			
			return "/edi/sale/NEDMSAL0020";
		}
		
		/**
		 * 매출정보 - > 매출정보(점포별)  
		 * @param NEDMSAL0020VO
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/sale/NEDMSAL0020Select.json", method = RequestMethod.POST)
		public @ResponseBody Map<String, Object> selectStore(@RequestBody NEDMSAL0020VO vo, HttpServletRequest request) throws Exception {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			Calendar cal = Calendar.getInstance();
			/*int nowHour = cal.get(Calendar.HOUR_OF_DAY);
			if(nowHour >= 4 && nowHour <= 6 ){
				return resultMap;
			}*/
			
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			vo.setVenCds(epcLoginVO.getVendorId());
			//String[] tmpDate = vo.getSrchEndDate().split("-");
			//vo.setSrchStartDate(tmpDate[0]+"-"+tmpDate[1]+"-01");
			
			List<NEDMSAL0020VO> saleList = nedmsa00020Service.selectStoreInfo(vo);

			resultMap.put("saleList", saleList);
			
			return resultMap;
		}
		
		/**
		 * 매출정보 - > 매출정보(점포별)   txt파일 생성
		 * @param NEDMSAL0020VO
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/edi/sale/NEDMSAL0020Text.do", method = RequestMethod.POST)
		public void createTextStore(NEDMSAL0020VO vo,HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		//	String[] tmpDate = vo.getSrchEndDate().split("-");
		//	vo.setSrchStartDate(tmpDate[0]+"-"+tmpDate[1]+"-01");
			
			
			// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			vo.setVenCds(epcLoginVO.getVendorId());
			
			
			nedmsa00020Service.createTextStore(vo,request,response);
		}
	
}
