package com.lottemart.epc.edi.usply.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.model.NEDMUSP0050VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0050Service;

/**
 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 Controller
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
public class NEDMUSP0050Controller {
	@Autowired
	private NEDMUSP0050Service nedmusp0050Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0050.do", method = RequestMethod.GET)
	public String usplyReason(Locale locale,  ModelMap model,HttpServletRequest request) {
		String returnPage = "/edi/usply/NEDMUSP0050";
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
		
		map.put("srchStartDate", commonUtil.firstDate(nowDate));
		map.put("srchEndDate", nowDate);
		
		model.addAttribute("paramMap",map);
		
		return returnPage;
	}
	
	/////////////////////////////////
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 조회
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0050Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectUsplyReason(@RequestBody NEDMUSP0050VO map, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		map.setVenCds(epcLoginVO.getVendorId());
		
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		 
		List<NEDMUSP0050VO> 	usplyList 	=  nedmusp0050Service.selectUsplyReasonInfo(map);
		resultMap.put("usplyList", usplyList);
		
		return resultMap;
	}
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 등록페이지
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0050UpdateSelect.do", method = RequestMethod.POST)
	public ModelAndView NselectUpdateUsplyReason(NEDMUSP0050VO map) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/edi/usply/NEDMUSP0051");
		//mv.addObject("usplyList", nedmusp0050Service.selectUsplyReasonInfo(map));
		mv.addObject("usplyList", nedmusp0050Service.selectUpdateUsplyReasonInfo(map));
		return mv;
	}
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 저장
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0050Update.json", method = RequestMethod.POST)
	public @ResponseBody NEDMUSP0050VO selectUsplyReasonUpdate(ModelMap model,@RequestBody NEDMUSP0050VO map, HttpServletRequest request) throws Exception {
		nedmusp0050Service.selectUsplyReasonUpdate(map);
		return map;
	}
	
	
	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0050Text.do", method=RequestMethod.POST)
    public void createTextOrdProdList(NEDMUSP0050VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());
		
		nedmusp0050Service.createTextOrdProdList(vo, request, response);
	}	
}
