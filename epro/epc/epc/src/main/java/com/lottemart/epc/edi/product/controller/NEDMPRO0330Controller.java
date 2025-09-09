package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0330VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0330Service;

import lcn.module.common.util.DateUtil;

@Controller
public class NEDMPRO0330Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0330Controller.class);

	@Autowired
	private NEDMPRO0330Service nedmpro0330Service;
	
	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	/**
	 * 약정서 화면 호출
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0330.do")
	public String NEDMPRO0320Init(ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList", 		commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));		//검색종료일
		model.addAttribute("nextMonth",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 31), "-"));	//한달뒤
		
		return "/edi/product/NEDMPRO0330";
	}
	

	/**
	 * 약정서 조회
	 * @param request
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdRtnDocList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getProdRtnDocList(@RequestBody NEDMPRO0330VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0330Service.getProdRtnDocList(paramVo, request);
	}
	
	/**
	 * 반품 약정서 조회( 상품별, 점포 & 상품별 )
	 * @param request
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdRtnCntrStrProdList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectProdRtnCntrStrProdList(@RequestBody NEDMPRO0330VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0330Service.selectProdRtnCntrStrProdList(paramVo, request);
	}
	
	
	
	
	
	
	
	
}
