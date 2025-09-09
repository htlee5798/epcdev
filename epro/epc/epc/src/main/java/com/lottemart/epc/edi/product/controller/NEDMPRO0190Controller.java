package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.product.model.NEDMPRO0190VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0080Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0190Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0200Service;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0190Controller {
	
	@Resource (name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	NEDMPRO0080Service nedmpro0080service;
	
	@Autowired
	NEDMPRO0190Service nedmpro0190service;
	
	@Autowired
	NEDMPRO0200Service nedmpro0200service;

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0190Controller.class);
	
	/**
	 * 상품 영양성분속성 요청조회 페이지
	 * @param ModelMap
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0190.do")
	public String loadPageProdNutAttrInfo(ModelMap model, HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		List<HashMap> l1CdList = null;
		
		try {
		    l1CdList = nedmpro0080service.selectL1CdAll();	
		} catch(Exception e) {
			logger.error("openPageProdGrpAttr Error Message : " + e.getMessage());
		}
		
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("l1CdList", l1CdList);
		return "/edi/product/NEDMPRO0190";
	}
	
	/**
	 * 업체별 영양성분 값 가져오기 
	 * FROM JSP : NEDMPRO0080.jsp
	 * @param NEDMPRO0080VO
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getNutAttr.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> getProdGrpAttrInfo(@RequestBody NEDMPRO0190VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	nutAttrInfoMap = new HashMap<String, Object>();
		List<HashMap> nutAttrList = null;
		
		// 웹에서 요청하는 페이지 정보		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());
		
		nutAttrInfoMap.put("paginationInfo", paginationInfo);		
		
		// 페이징관리객체 정보 값객체에 페이징정보 할당
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		try {
			// 페이징정보 포함한 요청값으로 영양성분 조회
			nutAttrList = nedmpro0190service.selectNutAttrInfo(vo);
			
			nutAttrInfoMap.put("nutAttrInfoList", nutAttrList);			
			nutAttrInfoMap.put("pageIdx", vo.getPageIndex());

			// 페이징정보 없는 요청값으로 상품데이터 전체 조회
			int cntGrpAttrInfo = nedmpro0190service.countNutAttrInfo(vo);
			
			paginationInfo.setTotalRecordCount(cntGrpAttrInfo);
			
			nutAttrInfoMap.put("pageAttrInfo", PagingUtil.makingPagingContents(request, paginationInfo, "text", "pd.fetchProdAttrInfo"));					
		} catch(Exception e) {
			logger.error("nutProdGrpAttr Error Message : " + e.getMessage());
		}
		
		return nutAttrInfoMap;
	}
	
	/*
	 * 특정상품 영양성분 요청상태 페이지
	 * FROM JSP : NEDMPRO0190.jsp
	 * @param prodCd
	 * @param l3Cd
	 * @param aprvStat
	 * @param ModelMap
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/edi/product/NEDMPRO019001.do")
	public String openPopupNutAprvStat(@RequestParam(value = "prodCd",required = false) String prodCd, 
			@RequestParam(value = "l3Cd",required = false) String l3Cd,
			@RequestParam(value = "aprvStat",required = false) String aprvStat, ModelMap model, HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		try {	
			model.addAttribute("prodCd", prodCd);
			model.addAttribute("l3Cd", l3Cd);
			model.addAttribute("aprvStat", aprvStat);
		} catch(Exception e) {
			logger.error("openPopupNutAprvStat Error Message : " + e.getMessage());
		}
		
		model.addAttribute("epcLoginVO", epcLoginVO);
		return "/edi/product/NEDMPRO019001";
	}
	
	/**
	 * 특정상품 영양성분 요청상태 가져오기 
	 * FROM JSP : NEDMPRO019001.jsp
	 * @param NEDMPRO0190VO
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/edi/product/getProdNutAprv.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> selectProdNutAprv(@RequestBody NEDMPRO0190VO vo, HttpServletRequest request) throws Exception {
		List<HashMap> attrAprvInfoList = null;

		try {
		    Map<String, Object> paramMap = new HashMap<String, Object>();
			  
		    String prodCd = vo.getProdCd();
			int seqAttr = nedmpro0200service.getProdNutMaxSeq(prodCd);
			
			paramMap.put("prodCd", prodCd);
			paramMap.put("aprvStat", vo.getAprvStat());
			paramMap.put("l3Cd", vo.getL3Cd());
			paramMap.put("seq", seqAttr);
						
			attrAprvInfoList = nedmpro0190service.selectProdNutAprv(paramMap);
			
		} catch(Exception e) {
			logger.error("getAttrAprvInfoAProd Error Message : " + e.getMessage());
		}
		
		return attrAprvInfoList;
	}
	
	
}
 