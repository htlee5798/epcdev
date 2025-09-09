package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.product.model.EdiCdListCodeVO;
import com.lottemart.epc.edi.product.model.PEDMPRO0095VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0096VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0097VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0098VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0099VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0099Service;


/**
 * @Class Name : PEDMPRO0099Controller
 * @Description : 기존상품 수정 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자           	수정내용
 *  -------    --------    ---------------------------
 * 2015.11.10	SONG MIN KYO	최초생성
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

@Controller
public class PEDMPRO0099Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PEDMPRO0099Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name = "pEDMPRO0099Service")
	private PEDMPRO0099Service pEDMPRO0099Service;

	//공통. 브랜드, 팀분류 코드 조회용 서비스
	@Autowired
	private PEDPCOM0001Service commService;

	/**
	 * 페이지 호출
	 * @param searchParam
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0099.do")
    public String newProductRegistPage(SearchParam searchParam, Model model, HttpServletRequest request) {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		if (StringUtils.isBlank(searchParam.getPageIdx()) || StringUtils.trimToEmpty(searchParam.getPageIdx()).equals("")) {
			model.addAttribute("pageIdx", "0");
		} else {
			model.addAttribute("pageIdx", searchParam.getPageIdx());
		}

		return "edi/product/PEDMPRO0099";
	}


	/**
	 * 기존상품 등록현황
	 * @param searchParam
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0099.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> PEDMPRO0099(@RequestBody SearchProduct searchParam, HttpServletRequest request, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();

		Integer totalProductCount = 0;
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchParam.getPageIndex());
		paginationInfo.setRecordCountPerPage(Integer.parseInt(searchParam.getRecordCnt()));
		paginationInfo.setPageSize(searchParam.getPageSize());
		map.put("paginationInfo", paginationInfo);
		searchParam.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchParam.setLastIndex(paginationInfo.getLastRecordIndex());
		searchParam.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		searchParam.setVenCds(epcLoginVO.getVendorId());

		// List Total Count
		int totCnt = pEDMPRO0099Service.selectTotalProductCount(searchParam);

		// List 가져오기
		List<PEDMPRO0099VO> wholeProductList = pEDMPRO0099Service.selectWholeProductList(searchParam);
		paginationInfo.setTotalRecordCount(totCnt);


		// 화면에 보여줄 게시물 리스트
		map.put("resultList", 		wholeProductList);
		map.put("pageIdx", 		searchParam.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		return map;
	}

	/**
	 * 상품수정 페이지
	 * @param searchParam
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edi/product/updateWholeProduct.do")
	public String updateWholeProductInit(PEDMPRO0098VO pEDMPRO0098VO, HttpServletRequest request, Model model) {
		int GrpArrCnt							=	pEDMPRO0099Service.selectWholeGrpArrCnt(pEDMPRO0098VO);					// 기존상품 세분류에 매핑되어 있는 SAP_L3_CD 카운트 조회
		PEDMPRO0098VO	pedmpro0098vo			=	pEDMPRO0099Service.selectNewWholeProductOldTeamInfo(pEDMPRO0098VO);		// 기존상품 팀, 대분류, 세분류 정보 조회

		model.addAttribute("teamInfo", 			pedmpro0098vo);
		model.addAttribute("GrpArrCnt",			GrpArrCnt);

		//return  "edi/product/PEDMPRO0097";
		return  "edi/product/PEDMPRO0096";
	}

	/**
	 * 그룹분석속성 저장
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/insertGroupAttr.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertGroupAttr(@RequestBody PEDMPRO0096VO pEDMPRO0096VO, 	HttpServletRequest request) {
		return pEDMPRO0099Service.insertWholeProductAtt(pEDMPRO0096VO, request);
	}

	/**
	 * 그룹분석속성 삭제
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/deleteGroupAttr.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> deleteGroupAttr(@RequestBody PEDMPRO0096VO pEDMPRO0096VO, 	HttpServletRequest request) {
		return pEDMPRO0099Service.deleteGroupAttr(pEDMPRO0096VO, request);
	}


	/**
	 * 기존세분류에 매핑되어있는 SAP 소분류 콤보박스 리스트
	 * @param pEDMPRO0098VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectSapMapAttrList.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectSapMapAttrList(@RequestBody PEDMPRO0098VO pEDMPRO0098VO, 	HttpServletRequest request) {
		Map<String, Object> resultMap	=	new HashMap<String, Object>();
		List<PEDMPRO0099VO>	resultList	=	pEDMPRO0099Service.selectSapMapAttrList(pEDMPRO0098VO);

		resultMap.put("sapL3CdComboList", resultList);

		return resultMap;
	}

	/**
	 * sap 소분류에 따른 그룹분석속성 리스트 조회
	 * @param pEDMPRO0098VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectSapL3CdAttrList.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectSapL3CdAttrList(@RequestBody PEDMPRO0098VO pEDMPRO0098VO, 	HttpServletRequest request) {
		Map<String, Object> resultMap				=	new HashMap<String, Object>();
		List<PEDMPRO0097VO>	sapL3GrpAttrList		=	pEDMPRO0099Service.selectNewWholeProductAttrList(pEDMPRO0098VO);		// 그룹분석속성 분류코드 리스트 조회
		List<PEDMPRO0096VO>	sapL3GrpAttrComboList	=	pEDMPRO0099Service.selectNewWholeProductAttComboList(pEDMPRO0098VO);	// 그룹분석속성 분류코드의 콤보리스트 조회
		List<PEDMPRO0095VO>	kyekeokList				=	pEDMPRO0099Service.selectkyekeokList(pEDMPRO0098VO);					// 규격단위 리스트 조회


		resultMap.put("sapL3GrpAttrList", 		sapL3GrpAttrList);
		resultMap.put("sapL3GrpAttrComboList", 	sapL3GrpAttrComboList);
		resultMap.put("kyekeokList", 			kyekeokList);

		return resultMap;
	}

	/**
	 * 상품분석속성 관리 (일괄) 페이지 호출
	 * @param searchParam
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0900.do")
    public String wholeProductUpdateNewPropListBatchInit(SearchProduct searchParam, HttpServletRequest request, Model model) {

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		searchParam.setVenCds(epcLoginVO.getVendorId());

		if (StringUtils.isBlank(searchParam.getPageIdx()) || StringUtils.trimToEmpty(searchParam.getPageIdx()).equals("")) {
			model.addAttribute("pageIdx", "0");
		} else {
			model.addAttribute("pageIdx", searchParam.getPageIdx());
		}

		// 팀리스트(현재 사용안함)
		model.addAttribute("teamList", 	 commService.selectDistinctTeamList());
		//model.addAttribute("l1CdList", 	 pEDMPRO0099Service.selectProductL1CdListBatch(searchParam));

		model.addAttribute("epcLoginVO", epcLoginVO);

		//return "edi/product/PEDMPRO0900";
		return "edi/product/PEDMPRO1000";
	}

	/**
	 * 상품속성관리 (일괄) 리스트 json
	 * @param searchParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectWholeProductAttrBatchList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductAttrBatchList(@RequestBody SearchProduct searchParam, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer totalProductCount = 0;
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchParam.getPageIndex());
		paginationInfo.setRecordCountPerPage(Integer.parseInt(searchParam.getRecordCnt()));
		paginationInfo.setPageSize(searchParam.getPageSize());
		map.put("paginationInfo", paginationInfo);
		searchParam.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchParam.setLastIndex(paginationInfo.getLastRecordIndex());
		searchParam.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		searchParam.setVenCds(epcLoginVO.getVendorId());

		// List Total Count
		int totCnt = pEDMPRO0099Service.selectNewWholeProductCountBatch(searchParam);

		// List 가져오기
		List<PEDMPRO0099VO> result = pEDMPRO0099Service.selectNewWholeProductListBatch(searchParam);
		paginationInfo.setTotalRecordCount(totCnt);

		PEDMPRO0098VO pEDMPRO0098VO	=	new PEDMPRO0098VO();
		pEDMPRO0098VO.setNewProductCode("");
		pEDMPRO0098VO.setSellCode("");
		pEDMPRO0098VO.setSrchL4Cd(searchParam.getL4Cd());
		pEDMPRO0098VO.setMajorCd(searchParam.getMajorCd());
		pEDMPRO0098VO.setEntpCd(searchParam.getEntpCode());
		pEDMPRO0098VO.setL1Cd(searchParam.getL1Cd());
		pEDMPRO0098VO.setSapL3Cd(searchParam.getSapL3Cd());
		pEDMPRO0098VO.setSrchGrpCd(searchParam.getSrchGrpCd());

		List<PEDMPRO0097VO>	sapL3GrpAttrList		=	pEDMPRO0099Service.selectProductBatchGrpAttrList(pEDMPRO0098VO);				// 그룹분석속성리스트
		List<PEDMPRO0096VO>	sapL3GrpAttrComboList	=	pEDMPRO0099Service.selectProductGrpAttrComboListBatch(pEDMPRO0098VO);			// 그룹분석속성 분류코드의 콤보리스트 조회
		List<PEDMPRO0095VO>	kyekeokList				=	pEDMPRO0099Service.selectkyekeokList(pEDMPRO0098VO);						// 규격단위 리스트 조회


		// 화면에 보여줄 게시물 리스트
		map.put("resultList", 		result);
		map.put("pageIdx", 		searchParam.getPageIndex());


		map.put("sapL3GrpAttrList", 			sapL3GrpAttrList);
		map.put("sapL3GrpAttrComboList", 		sapL3GrpAttrComboList);
		map.put("kyekeokList", 					kyekeokList);


		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		return map;
	}

	/**
	 * 대분류 콤보박스 리스트 조회
	 * @param searchParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectWholeProductAttrBatchTeamCombo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductAttrBatchTeamCombo(@RequestBody SearchProduct searchParam, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		searchParam.setVenCds(epcLoginVO.getVendorId());

		List<EdiCdListCodeVO> resultL1List = pEDMPRO0099Service.selectProductL1CdListBatch(searchParam);

		//대분류 리스트
		map.put("resultList", resultL1List);

		return map;
	}

	/**
	 * 상품 분석속성관리 (일괄) 세분류 조회
	 * @param searchParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectProductBatchL4CdList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProductBatchL4CdList(@RequestBody SearchProduct searchParam, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		searchParam.setVenCds(epcLoginVO.getVendorId());

		List<EdiCdListCodeVO> resultL1List = pEDMPRO0099Service.selectProductL4CdListBatch(searchParam);

		//
		map.put("resultList", resultL1List);

		return map;
	}

	/**
	 * 상품속성관리(일괄)저장
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/insertGroupAttrBatch.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertGroupAttrBatch(@RequestBody PEDMPRO0096VO pEDMPRO0096VO, 	HttpServletRequest request) {
		return pEDMPRO0099Service.insertWholeProductAttBatch(pEDMPRO0096VO, request);
	}

	/**
	 * 상품속성관리(일괄) 삭제
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/delGroupAttrBatch.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> delGroupAttrBatch(@RequestBody PEDMPRO0096VO pEDMPRO0096VO, 	HttpServletRequest request) {
		return pEDMPRO0099Service.delWholeProductAttBatch(pEDMPRO0096VO, request);
	}

	/**
	 * 상품속성관리(일괄)  sap 소분류에 따른 그룹분석속성리스트 조회
	 * @param pEDMPRO0098VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectSapL3CdAttrListBatch.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectSapL3CdAttrListBatch(@RequestBody PEDMPRO0098VO pEDMPRO0098VO, 	HttpServletRequest request) {
		Map<String, Object> resultMap				=	new HashMap<String, Object>();
		List<PEDMPRO0097VO>	sapL3GrpAttrList		=	pEDMPRO0099Service.selectNewWholeProductAttrListBatch(pEDMPRO0098VO);		// 그룹분석속성 분류코드 리스트 조회
		List<PEDMPRO0096VO>	sapL3GrpAttrComboList	=	pEDMPRO0099Service.selectNewWholeProductAttComboList(pEDMPRO0098VO);		// 그룹분석속성 분류코드의 콤보리스트 조회
		List<PEDMPRO0095VO>	kyekeokList				=	pEDMPRO0099Service.selectkyekeokList(pEDMPRO0098VO);						// 규격단위 리스트 조회


		resultMap.put("sapL3GrpAttrList", 		sapL3GrpAttrList);
		resultMap.put("sapL3GrpAttrComboList", 	sapL3GrpAttrComboList);
		resultMap.put("kyekeokList", 			kyekeokList);

		return resultMap;
	}










	/*여기서 부터 추가작업 2015.11.0
	 *
	 *
	 */
	/**
	 * 분석속성(일괄) 해당상품들의 그룹분석속성&마트단독속성 리스트
	 * @param pEDMPRO0098VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectProductBatchGrpAttrList.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProductBatchGrpAttrList(@RequestBody PEDMPRO0098VO pEDMPRO0098VO, 	HttpServletRequest request) {
		Map<String, Object> resultMap				=	new HashMap<String, Object>();

		List<PEDMPRO0097VO>	sapL3GrpAttrList		=	pEDMPRO0099Service.selectProductBatchGrpAttrList(pEDMPRO0098VO);	// 그룹분석속성리스트
		List<PEDMPRO0096VO>	sapL3GrpAttrComboList	=	pEDMPRO0099Service.selectProductGrpAttrComboList(pEDMPRO0098VO);	// 그룹분석속성 분류코드의 콤보리스트 조회
		List<PEDMPRO0095VO>	kyekeokList				=	pEDMPRO0099Service.selectkyekeokList(pEDMPRO0098VO);				// 규격단위 리스트 조회

		resultMap.put("sapL3GrpAttrList", 		sapL3GrpAttrList);
		resultMap.put("sapL3GrpAttrComboList", 	sapL3GrpAttrComboList);
		resultMap.put("kyekeokList", 			kyekeokList);

		return resultMap;
	}



	/**
	 * 해당상품의 그룹분석속성 리스트 조회
	 * @param pEDMPRO0098VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectProductGrpAttrList.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProductGrpAttrList(@RequestBody PEDMPRO0098VO pEDMPRO0098VO, 	HttpServletRequest request) {
		Map<String, Object> resultMap				=	new HashMap<String, Object>();

		List<PEDMPRO0097VO>	sapL3GrpAttrList		=	pEDMPRO0099Service.selectProductGrpAttrList(pEDMPRO0098VO);			// 그룹분석속성리스트
		List<PEDMPRO0096VO>	sapL3GrpAttrComboList	=	pEDMPRO0099Service.selectProductGrpAttrComboList(pEDMPRO0098VO);	// 그룹분석속성 분류코드의 콤보리스트 조회
		List<PEDMPRO0095VO>	kyekeokList				=	pEDMPRO0099Service.selectkyekeokList(pEDMPRO0098VO);				// 규격단위 리스트 조회

		resultMap.put("sapL3GrpAttrList", 		sapL3GrpAttrList);
		resultMap.put("sapL3GrpAttrComboList", 	sapL3GrpAttrComboList);
		resultMap.put("kyekeokList", 			kyekeokList);

		return resultMap;
	}

	/**
	 * 상품속성관리(일괄) 일괄완료 처리
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/updateCompleteGroupAttrBatch.json")
	public @ResponseBody Map<String, Object> updateCompleteGroupAttrBatch(@RequestBody PEDMPRO0096VO pEDMPRO0096VO, 	HttpServletRequest request) {
		return pEDMPRO0099Service.updateCompleteGroupAttrBatch(pEDMPRO0096VO, request);
	}

	/**
	 * 상품속성관리 완료처리
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/updateCompleteGroupAttr.json")
	public @ResponseBody Map<String, Object> updateCompleteGroupAttr(@RequestBody PEDMPRO0096VO pEDMPRO0096VO, 	HttpServletRequest request) {
		return pEDMPRO0099Service.updateCompleteGroupAttr(pEDMPRO0096VO, request);
	}

	/**
	 * SAP 소분류에 매핑되어 있는 그룹소분류
	 * @param pEDMPRO0098VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/selectSrchGrpCd.json")
	public @ResponseBody Map<String, Object> selectSrchGrpCd(@RequestBody PEDMPRO0098VO pEDMPRO0098VO, HttpServletRequest request) {
		return pEDMPRO0099Service.selectSrchGrpCd(pEDMPRO0098VO);
	}
}
