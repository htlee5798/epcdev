package com.lottemart.epc.edi.product.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.AppException;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.service.NEDMPRO0010Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0010Controller
 * @Description : 신상품현황 조회 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21 	SONG MIN KYO	최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0010Controller extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0010Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;


	@Resource(name="nEDMPRO0010Service")
	private NEDMPRO0010Service nEDMPRO0010Service;

	/**
	 * 신규상품등록 (온오프 전용) 화면 Form 호출
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0010.do")
	public String newProdFixListFormInit(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new IllegalArgumentException();
		}

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", 		epcLoginVO);
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));		//검색종료일
		return "edi/product/NEDMPRO0010";
	}

	/**
	 * 신상품 현황 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectNewProdFixList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectNewProdFixList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (paramMap == null || request == null) {
			throw new IllegalArgumentException();
		}

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		paramMap.put("venCds", epcLoginVO.getVendorId());

		String dateRegex = "([1-9])\\d{3}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
		Pattern pattern = Pattern.compile(dateRegex);
		Matcher dateMatcher = null;
		String srchFromDt = String.valueOf(paramMap.get("srchFromDt"));
		String srchEndDt = String.valueOf(paramMap.get("srchEndDt"));

		dateMatcher = pattern.matcher(srchFromDt);
		if (!(srchFromDt != null && !"".equals(srchFromDt) && dateMatcher.find())) {
			//logger.error("검색 시작일을 정확히 입력해주세요.");
			throw new AppException("검색 시작일을 정확히 입력해주세요.");
		}

		dateMatcher = pattern.matcher(srchEndDt);
		if (!(srchEndDt != null && !"".equals(srchEndDt) && dateMatcher.find())) {
			//logger.error("검색 종료일을 정확히 입력해주세요.");
			throw new AppException("검색 종료일을 정확히 입력해주세요.");
		}

		return nEDMPRO0010Service.selectNewProdFixList(paramMap);
	}

	/**
	 * 신상품현황조회 이미지 등록 팝업[한건씩 등록할때 & 일괄적용할떄]
	 * @param paramMap
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/newProdImgUploadPop.do")
	public String newProdImgUploadPop(@RequestParam Map<String, Object> paramMap, ModelMap model, HttpServletRequest request) throws Exception {
		return "edi/product/NEDMPRO0010ImgPop";
	}

	/**
	 * 신상품현황 조회 POG 이미지 저장
	 * @param paramMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertNewProdImgPop.do")
	public String insertNewProdImgPop(@RequestParam Map<String, Object> paramMap,	HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("result", nEDMPRO0010Service.insertNewProdImgPop(paramMap, request));
		return "edi/product/NEDMPRO0010ImgPop";
	}
}
