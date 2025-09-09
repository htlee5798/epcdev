package com.lottemart.epc.edi.order.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.order.model.NEDMORD0010VO;
import com.lottemart.epc.edi.order.service.NEDMORD0010Service;

/**
 * 발주정보 - > 기간정보  - > 상품별 Controller
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
public class NEDMORD0010Controller {

	@Autowired
	private NEDMORD0010Service service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 발주정보(상품별) 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0010.do")
	public String init(ModelMap model, HttpServletRequest request) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven = ven.substring(0, ven.length()-1);
		map.put("ven", ven);	//session 협력업체코드 All

		//Defalut 날짜 입력(현재날짜)
		map.put("srchStartDate", nowDate);
		map.put("srchEndDate", nowDate);

		model.addAttribute("paramMap",map);

		return "/edi/order/NEDMORD0010";
	}

	/**
	 * 기간정보 - > 상품별 조회
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0010Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> productList(@RequestBody NEDMORD0010VO vo, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMORD0010VO> orderList = service.selectOrdPordList(vo);

		resultMap.put("orderList", orderList);

		return resultMap;
	}

	/**
	 * 발주정보(상품별) txt파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/order/NEDMORD0010Text.do", method=RequestMethod.POST)
    public void createTextOrdProdList(NEDMORD0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*String[] stors = map.get("storeVal").toString().split("-");

		if(!"".equals(stors[0])){
			map.put("storeVal", stors);     //점포코드
		}*/

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		service.createTextOrdProdList(vo, request, response);
	}

}
