package com.lottemart.epc.edi.product.controller;

/**
 * 상품 - > 상품현황관리 -> PB성적서 관리 CONTROLLER
 *
 * @author IL YOUNG PARK
 * @since 2022.03.07
 * @version 1.0
 * @see
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lcn.module.framework.property.ConfigurationService;

import com.lottemart.epc.edi.product.dao.NEDMPRO0180Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0180VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0180Service;
import com.lottemart.epc.common.model.EpcLoginVO;

@Controller
public class NEDMPRO0180Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0180Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private NEDMPRO0180Service nEDMPRO180Service;

	@Autowired
	private NEDMPRO0180Dao nEDMPRO0180Dao;

	@RequestMapping(value = "/edi/product/NEDMPRO0180.do")
	public String SelectPbProdReport(ModelMap model, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		Map<String, String> map = new HashMap();
		
		if ("".equals(epcLoginVO.getAdminId()) || epcLoginVO.getAdminId() != "") {
			List<String> adminDepts = nEDMPRO180Service.selectAdminDeptPbReport(epcLoginVO.getAdminId());
			if (adminDepts.size() == 0) {
				model.addAttribute("IsAdminDept", "N");
			} else {
				model.addAttribute("IsAdminDept", "Y");
			}
		}
		model.addAttribute("epcLoginVO", epcLoginVO);
		
		// 성적서 리스트 엑셀 출력시 협력업체번호 보이도록
		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);
		model.addAttribute("paramMap",map);
		
		return "edi/product/NEDMPRO0180";
	}
	
	/**
	 * 상품별 성적서 정보리스트 불러오기
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0180Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProductList(@RequestBody NEDMPRO0180VO vo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setEntpCds(epcLoginVO.getVendorId());
		map.put("loginId", epcLoginVO.getAdminId());

		// PB상품 성적서 리스트 불러오기
		try {
			List<NEDMPRO0180VO> resultList = nEDMPRO180Service.selectPbProdList(vo);
			map.put("resultList", resultList);
		} catch (Exception e) {
			logger.error("ERROR : " + e);
		}

		return map;
	}

	/**
	 * 특정 상품 성적서 정보 팝업창
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO018001.do", method=RequestMethod.POST)
	public String RegisterPbProdReport(ModelMap model, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		NEDMPRO0180VO nEDMPRO0180VO = null;
		NEDMPRO0180VO paramsVO = new NEDMPRO0180VO();

		String prodCd = StringUtils.trimToEmpty(request.getParameter("prodCd"));
		String prodNm = StringUtils.trimToEmpty(request.getParameter("prodNm"));

		// 현재 등록된 상품 순번 가져오기
		paramsVO.setProdCd(prodCd);
		nEDMPRO0180VO = nEDMPRO0180Dao.selectSeqRecentReportFile(paramsVO);
		String seq = null;

		// 등록된적 없는 상품의 경우 초기 순번 1로
		if (nEDMPRO0180VO == null || nEDMPRO0180VO.getSeq() == null) {
			seq = "1";
		} else {
			seq = nEDMPRO0180VO.getSeq();
		}
		paramsVO.setSeq(seq);

		Map<String, String> paramMap = new HashMap<String, String>();
		// 불러온 순번 + 상품 코드로 상품 조회 
		NEDMPRO0180VO prodReportInfo = nEDMPRO180Service.selectPbProdReportInfo(paramsVO);
		if (prodReportInfo != null) {
			paramMap.put("srcFileNm", prodReportInfo.getSrcFileNm());
			paramMap.put("uploadDt", prodReportInfo.getUploadDt());
			paramMap.put("expireDt", prodReportInfo.getExpireDt());
			paramMap.put("prodType", prodReportInfo.getProdType());
		}

		paramMap.put("prodCd", prodCd);
		paramMap.put("prodNm", prodNm);
		paramMap.put("seq", seq);

		model.addAttribute("prodInfo", paramMap);

		return "edi/product/NEDMPRO018001";
	}

	/**
	 * 성적서 등록 상품 존재시, 알림 팝업창
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO018002.do", method={RequestMethod.GET,RequestMethod.POST})
	public String showPbReportValidMsg(ModelMap model, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		NEDMPRO0180VO nEDMPRO0180VO = new NEDMPRO0180VO();
		nEDMPRO0180VO.setEntpCds(epcLoginVO.getVendorId());

		Integer countNotValidPbProdReport = nEDMPRO180Service.countNotValidPbProdReport(nEDMPRO0180VO);

		model.addAttribute("countNotValidPbProdReport", countNotValidPbProdReport);

		return "edi/product/NEDMPRO018002";
	}

	/**
	 * 해당 협력업체내 성적서업로드 필요한 상품 개수 가져오기
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO018003.do", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> checkEntpCdPbReport(ModelMap model, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		NEDMPRO0180VO nEDMPRO0180VO = new NEDMPRO0180VO();
		nEDMPRO0180VO.setEntpCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 해당 사업자 번호에 매핑된 협력업체 중
		// 등록이 필요한 성적서의 개수 불러오기
		Integer countNotValidPbProdReport = nEDMPRO180Service.countNotValidPbProdReport(nEDMPRO0180VO);
		resultMap.put("countNotValidPbProdReport", countNotValidPbProdReport);

		return resultMap;
	}

	/**
	 * 상품별 유효한 성적서 파일 리스트 불러오기 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO018004.do", method=RequestMethod.POST)
	public String selectListPbReportFile(ModelMap model, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		String prodCd = StringUtils.trimToEmpty(request.getParameter("prodCd"));

		List<NEDMPRO0180VO> listPbReportFile = nEDMPRO0180Dao.selectReportFileListForAProd(prodCd);
		model.addAttribute("listPbReportFile", listPbReportFile);

		return "edi/product/NEDMPRO018004";
	}

	/**
	 * 특정 상품 성적서 정보 업데이트
	 * @param model
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateReportPbProd.do", method=RequestMethod.POST)
	public String updateReportPbProd(ModelMap model, @ModelAttribute NEDMPRO0180VO nEDMPRO0180VO, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMPRO0180VO.setRegId(epcLoginVO.getRepVendorId());

		Map<String, String> paramMap = new HashMap<String, String>();
		String msg = null;

		boolean isSuccess = nEDMPRO180Service.updateReportFile(nEDMPRO0180VO);
		if (!isSuccess) {
			msg = "-1";
			model.put("resultMsg", msg);
			return "edi/product/NEDMPRO018001";
		}

		paramMap.put("prodCd", nEDMPRO0180VO.getProdCd());
		paramMap.put("prodNm", nEDMPRO0180VO.getProdNm());
		paramMap.put("uploadDt", nEDMPRO0180VO.getUploadDt());
		paramMap.put("srcFileNm", nEDMPRO0180VO.getSrcFileNm());
		paramMap.put("prodType", nEDMPRO0180VO.getProdType());
		paramMap.put("prodTypeName", nEDMPRO0180VO.getProdTypeName());
		paramMap.put("seq", nEDMPRO0180VO.getSeq());
		model.put("prodInfo", paramMap);

		msg = "100";

		model.put("resultMsg", msg);

		return "edi/product/NEDMPRO018001";
	}
	
	/**
	 * 성적서 정보 갱신하기
	 * @param model
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateNewReportPbProd.do", method=RequestMethod.POST)
	public String updateNewReportPbProd(ModelMap model, @ModelAttribute NEDMPRO0180VO nEDMPRO0180VO, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMPRO0180VO.setRegId(epcLoginVO.getRepVendorId());

		Map<String, String> paramMap = new HashMap<String, String>();
		String msg = null;

		int cntReport = nEDMPRO180Service.countReportFileForAProd(nEDMPRO0180VO);
		boolean isDeleted = false;

		// 성적서 파일 개수가 8개를 넘어갈시
		// 가장 오래된 성적서 파일 정보 수정 및 파일 삭제
		if (cntReport > 8) {
			NEDMPRO0180VO delVO = nEDMPRO0180Dao.selectOldSeqReportFile(nEDMPRO0180VO);
			isDeleted = nEDMPRO180Service.deleteReportFile(delVO);

			if (!isDeleted) {
				msg = "-2";
				model.put("resultMsg", msg);
				return "edi/product/NEDMPRO018001";
			}
		}

		NEDMPRO0180VO recentReportInfo = nEDMPRO0180Dao.selectSeqRecentReportFile(nEDMPRO0180VO);
		nEDMPRO0180VO.setSeq(Integer.toString(Integer.parseInt(recentReportInfo.getSeq()) + 1)); // 저장해야 하는 순번

		boolean isSuccess = nEDMPRO180Service.updateReportFile(nEDMPRO0180VO);

		if (!isSuccess) {
			msg = "-3";
			model.put("resultMsg", msg);
			return "edi/product/NEDMPRO018001";
		}

		paramMap.put("prodCd", nEDMPRO0180VO.getProdCd());
		paramMap.put("prodNm", nEDMPRO0180VO.getProdNm());
		paramMap.put("uploadDt", nEDMPRO0180VO.getUploadDt());
		paramMap.put("expireDt", nEDMPRO0180VO.getExpireDt());
		paramMap.put("srcFileNm", nEDMPRO0180VO.getSrcFileNm());
		paramMap.put("prodType", nEDMPRO0180VO.getProdType());
		paramMap.put("prodTypeName", nEDMPRO0180VO.getProdTypeName());
		paramMap.put("seq", nEDMPRO0180VO.getSeq());
		model.put("prodInfo", paramMap);

		msg = "101";
		model.put("resultMsg", msg);

		return "edi/product/NEDMPRO018001";
	}

	/**
	 * 성적서 정보 삭제하기
	 * @param model
	 * @param vo
	 * @param request
	 * @param redirectAttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteReportPbProd.do", method=RequestMethod.POST)
	public String DeletePbProdReport(ModelMap model, @ModelAttribute NEDMPRO0180VO nEDMPRO0180VO, HttpServletRequest request, RedirectAttributes redirectAttr ) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMPRO0180VO.setRegId(epcLoginVO.getRepVendorId());

		String msg = null;
		boolean isDeleted = nEDMPRO180Service.deleteReportFile(nEDMPRO0180VO);

		Map<String, String> paramMap = new HashMap<String, String>();

		if (isDeleted) {
			paramMap.put("prodCd", nEDMPRO0180VO.getProdCd());
			paramMap.put("prodNm", nEDMPRO0180VO.getProdNm());
			model.put("prodInfo", paramMap);

			msg = "102";
			model.put("resultMsg", msg);

			return "edi/product/NEDMPRO018001";
		}

		redirectAttr.addAttribute("prodCd", nEDMPRO0180VO.getProdCd());
		redirectAttr.addAttribute("prodNm", nEDMPRO0180VO.getProdNm());

		msg = "-4";
		model.put("resultMsg", msg);

		return "redirect:/edi/product/NEDMPRO018001.do";
	}

	/**
	 * 파트너사별 필요 성적서수 리스트
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO018005.do", method=RequestMethod.POST)
	public String selectListEntpCdNotValidReport(ModelMap model, @ModelAttribute NEDMPRO0180VO nEDMPRO0180VO, HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		List<HashMap> resultMap = new ArrayList<HashMap>();
		String msg = null;
		String admFg = "F";
		if (epcLoginVO != null && !"".equals(epcLoginVO.getAdminId())) {
			admFg = "T";
		}

		try {
			resultMap = nEDMPRO0180Dao.selectEntpCdForNotValidReport(admFg);
			msg = "SUCCESS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "FAIL";
		}

		model.put("resultMsg", msg);
		model.put("entpCdListNotValidReport", resultMap);

		return "edi/product/NEDMPRO018005";
	}

	/**
	 * 성적서 다운로드
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/downloadReportPbProd.do", method = RequestMethod.POST)
	public void downloadPbReport(NEDMPRO0180VO nEDMPRO0180VO, HttpServletRequest request, HttpServletResponse response) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMPRO0180VO.setRegId(epcLoginVO.getRepVendorId());

		boolean isDownloadPbReport = nEDMPRO180Service.downloadReportFile(nEDMPRO0180VO, response);
		if (isDownloadPbReport) {
			/* 다운로드 성공시 */
		}
		/* 실패ㅣ */

	}
}
