package com.lottemart.epc.edi.product.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.product.model.NEDMPRO0130VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0130Service;

@Controller
public class NEDMPRO0130Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0130Controller.class);

	@Autowired
	private NEDMPRO0130Service nedmpro0130Service;

	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0130.do")
	public String NEDMPRO0130Init(ModelMap model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		model.addAttribute("epcLoginVO", epcLoginVO);

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		model.addAttribute("currentSecond", 		Long.toString(milliSecond));
		model.put("srchStartDate", commonUtil.nowDateBack(nowDate));
		model.put("srchEndDate", commonUtil.nowDateBack(nowDate));
		model.addAttribute("imagePath" , ConfigUtils.getString("system.cdn.static.path"));
		model.addAttribute("imgSubPath", ConfigUtils.getString("system.cdn.static.path.sub"));

		return "/edi/product/NEDMPRO0130";
	}

	/**
	 * 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/buy/NEDMPRO0130Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductList(@RequestBody NEDMPRO0130VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCode() == null || vo.getSrchEntpCode().equals("")) {
			map.put("resultList", "");
		}

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));

		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String applyDy = DateUtil.addDay(nowDate, 1);

		map.put("applyDy", applyDy);

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());

		map.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		vo.setVenCds(epcLoginVO.getVendorId());

		// List Total Count
		int totCnt = nedmpro0130Service.selectProductImageCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<NEDMPRO0130VO>	resultList 	= 	nedmpro0130Service.selectProductImageList(vo);
		map.put("resultList", resultList);

		// 화면에 보여줄 게시물 리스트
		map.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		map.put("currentSecond", Long.toString(milliSecond));
		return map;
	}

	/**
	 * 사이즈 수정 Init
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0131.do")
	public String NEDMPRO0131Init(ModelMap model, HttpServletRequest request) throws Exception {
		return "/edi/product/NEDMPRO0131";
	}

	/**
	 * 사이즈 변경 예약
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertMDSizeReserv.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertMDSizeReserv(@RequestBody NEDMPRO0130VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();
		//map = nedmpro0130Service.insertMDSizeReserv(vo, request);

		try {
			map	=	nedmpro0130Service.insertMDSizeReserv(vo, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("이미지 사이즈 변경예약 Exception 발생 ===============================================================================" + e.getMessage());
		}

		return map;

	}

	/**
	 * 이미지 등록/수정 Init
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0132.do")
	public String NEDMPRO0132Init(ModelMap model, HttpServletRequest request) throws Exception {
		String nowTime  = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss", Locale.KOREA);

		model.addAttribute("nowTime", nowTime);
		return "/edi/product/NEDMPRO0132";
	}

	/**
	 * 이미지 저장
	 * @param paramMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertSaleImgAllApply.do")
	public String insertSaleImgAllApply(@RequestParam Map<String, Object> paramMap,	HttpServletRequest request, Model model) throws Exception {

		String result = "FAIL";

		try {
			result = nedmpro0130Service.insertSaleImgAllApply(paramMap, request);
		}catch(Exception e) {
			logger.error("기존상품 이미지 저장 Exception 발생 ::::::::::::::::::::::::" + e.toString());
			result = "FAIL";
		}

		model.addAttribute("result", result);
		return "/edi/product/NEDMPRO0132";
	}

	/**
	 * 중량정보 수정 요청 팝업 페이지
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0133.do")
	public String NEDMPRO0133Init(ModelMap model, HttpServletRequest request) throws Exception {
		String nowTime  = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss", Locale.KOREA);

		HashMap<String, String> paramMap = new HashMap<>();
		String srcmkCd = request.getParameter("srcmkCd");
		String prodCd = request.getParameter("prodCd");

		paramMap.put("srcmkCd", srcmkCd);
		paramMap.put("prodCd", prodCd);

		HashMap resultMap = nedmpro0130Service.selectWgtInfo(paramMap);
		model.addAttribute("wgtInfo", resultMap);

		HashMap resMap = nedmpro0130Service.selectWgtResInfo(resultMap);
		model.addAttribute("wgtResInfo", resMap);

		model.addAttribute("nowTime", nowTime);
		return "/edi/product/NEDMPRO0133";
	}

	/**
	 * 중량정보 수정 요청
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/requestWgtInfoMod.json", method= RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> requestWgtInfoMod(@RequestBody NEDMPRO0130VO nedmpro0130vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		String resultCode = "";
		try {
			resultCode = nedmpro0130Service.submitWgtInfo(nedmpro0130vo);
			resultMap.put("resultCode", resultCode);

			// resultCode 1 일때 성공
			if (!resultCode.equals("1")) {
				throw new Exception();
			}
			resultMap.put("msg", "SUCCESS");
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("msg", "FAIL");
		}

		return resultMap;
	}
}
