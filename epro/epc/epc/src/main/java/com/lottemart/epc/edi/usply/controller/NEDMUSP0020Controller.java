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

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.model.NEDMUSP0020VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0020Service;
/**
 * 미납정보 - > 기간정보  - > 점포별 Controller
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
public class NEDMUSP0020Controller {
	//private final String version = "new"; //new old
	@Autowired
	private NEDMUSP0020Service nedmusp0020Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 미납정보 - > 기간정보  - > 점포별 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0020.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String store(Locale locale,  ModelMap model,HttpServletRequest request) {
		String returnPage = "/edi/usply/NEDMUSP0020";
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
		map.put("ven", ven);
		if(srchStartDate != null && !"".equals(srchStartDate)){
			//Defalut 날짜 입력(현재날짜)
			map.put("srchStartDate", srchStartDate);
			map.put("srchEndDate", srchStartDate);
		}else{
			//Defalut 날짜 입력(현재날짜)
			map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
			map.put("srchEndDate", commonUtil.nowDateBack(nowDate));
		}
		model.addAttribute("paramMap",map);

		return returnPage;
	}

	/////////////////////////////////

	/**
	 * 미납정보 - > 기간정보  - > 점포별 조회
	 * @param NEDMUSP0020VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0020Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectStore(@RequestBody NEDMUSP0020VO map, HttpServletRequest request) throws Exception {


		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		map.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		List<NEDMUSP0020VO> 	usplyList 	= 	nedmusp0020Service.selectStoreInfo(map);


		resultMap.put("usplyList", usplyList);

		return resultMap;
	}

	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/usply/NEDMUSP0020Text.do", method=RequestMethod.POST)
    public void createTextOrdProdList(NEDMUSP0020VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		nedmusp0020Service.createTextOrdProdList(vo, request, response);
	}
}
