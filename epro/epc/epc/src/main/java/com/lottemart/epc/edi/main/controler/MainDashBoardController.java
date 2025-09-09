package com.lottemart.epc.edi.main.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.main.model.MainDashBoardVO;
import com.lottemart.epc.edi.main.service.MainDashBoardService;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.service.CommonProductService;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class MainDashBoardController extends BaseController  {
	
	private static final Logger logger = LoggerFactory.getLogger(MainDashBoardController.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private MainDashBoardService mainDashBoardervice;
	
	@Autowired
	CommonProductService commonProductService;
	
	
	/**
	 * 메인화면 접근 Index (로그인 후)
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value= {"/main/mainDashBoard.do", "/main/vendorIntro.do"})
	public String inMainDashBoard(ModelMap model, HttpServletRequest request) throws Exception {
		
		Map<String,Object> mainMap = new HashMap<String,Object>();
		mainMap.put("defPgmID", "/edi/main/mainDashBoard.do");
		mainMap.put("pgm_code", "MAIN");
		
		model.addAttribute("paramMap", mainMap);
		
		return "/edi/main/ediIndex";
	}
	
	/**
	 * 메인화면 호출
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/main/mainDashBoard.do")
	public String mainDashBoardInfo(ModelMap model, HttpServletRequest request) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		model.addAttribute("today",			DateUtil.getToday("yyyy-MM-dd"));													// 현재일
		model.addAttribute("srchFromDt",	DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), -30), "-"));	// 30일전
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 30), "-"));	// 30일뒤
		model.addAttribute("teamList", 		commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		
		return "edi/main/mainDashBoard";
	}
	
	/**
	 * Main → 파트너사 실적 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectVenPrfr.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectVenPrfrList(@RequestBody MainDashBoardVO paramVo , HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectVenPrfrList(paramVo, request);
	}
	
	/**
	 * Main → 파트너사 매입액 조회 
	 * @param vo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectVenBuyList.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectVenBuyList(@RequestBody MainDashBoardVO paramVo , HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectVenBuyList(paramVo, request);
	}
	
	/**
	 * Main → 신상품 실적 조회
	 * @param vo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectNewProdPrfrList.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectNewProdPrfrList(@RequestBody MainDashBoardVO paramVo , HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectNewProdPrfrList(paramVo, request);
	}
	
	/**
	 * Main → 신상품 입점 제안
	 * @param vo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectProdNewPropList.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectProdNewPropList(@RequestBody MainDashBoardVO paramVo , HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectProdNewPropList(paramVo, request);
	}
	
	/**
	 * Main 원가변경요청 내역 및 상태 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectMainTpcProdChgCostItem.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectMainTpcProdChgCostItem(@RequestBody MainDashBoardVO paramVo , HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectMainTpcProdChgCostItem(paramVo, request);
	}
	
	/**
	 * Main 데이터 일괄 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object> 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectMainDashBoardAll.json", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectMainDashBoardAll(@RequestBody MainDashBoardVO paramVo , HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectMainDashBoardAll(paramVo, request);
	}
	
	/**
	 * Main 미납내역 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectMainNonPayItems.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectMainNonPayItems(@RequestBody MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectMainNonPayItems(paramVo, request);
	}
	
	/**
	 * Main 파트너사별 SKU 현황 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectMainVenSku.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectMainVenSku(@RequestBody MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectMainVenSku(paramVo, request);
	}
	
	/**
	 * Main 입고 거부 상품 미조치 내역 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/selectMainInboundRejects.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectMainInboundRejects(@RequestBody MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		return mainDashBoardervice.selectMainInboundRejects(paramVo, request);
	}
}
