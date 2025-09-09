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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0200VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0080Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0200Service;

import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0200Controller {
	
	@Resource (name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	NEDMPRO0080Service nedmpro0080service;
	
	@Autowired
	NEDMPRO0200Service nedmpro0200service;
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0200Controller.class);
	
	/**
	 * 상품 영양성분 요청조회 페이지
	 * @param ModelMap
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0200.do")
	public String showPageRegProdNutAttrInfo(ModelMap model, HttpServletRequest request) throws Exception {
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			
		List<HashMap> l1CdList = null;
		try {
		    l1CdList = nedmpro0080service.selectL1CdAll();	
		} catch(Exception e) {
			logger.error("openPageRegProdGrpAttrInfo Error Message : " + e.getMessage());
		}
		
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("l1CdList", l1CdList);
		return "/edi/product/NEDMPRO0200";
	}
	
	/**
	 * 상품 영양성분등록 페이지
	 * @param NEDMPRO0200VO
	 * @param ModelMap
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0201.do")
	public String selectAProdNutAttrInfo(NEDMPRO0200VO vo, ModelMap model, HttpServletRequest request) throws Exception {		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		Map<String, String> prodInfoSelected = new HashMap<String, String>();
		prodInfoSelected.put("prodCd" , vo.getProdCd());
		prodInfoSelected.put("l3Cd"   , vo.getL3Cd());
		prodInfoSelected.put("msg"    , vo.getMsg());
		
		int seqAttr = nedmpro0200service.getProdNutMaxSeq(vo.getProdCd());
		vo.setSeq(String.valueOf(seqAttr));
		int cntNotNutAttRes = nedmpro0200service.getCntNotNutAttRes(vo);
			
		model.addAttribute("epcLoginVO"      , epcLoginVO);
		model.addAttribute("prodInfo"        , prodInfoSelected);
		model.addAttribute("cntNotNutAttRes" , cntNotNutAttRes);
		model.addAttribute("seqAttr"         , seqAttr);
		return "/edi/product/NEDMPRO0201";
	}
    
	/**
	 * 상품 영양성분 가져오기 
	 * FROM JSP : NEDMPRO0201.jsp
	 * @param NEDMPRO0200VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getNutInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> getNutAttInfo(@RequestBody NEDMPRO0200VO vo) throws Exception {
		List<HashMap> nutAttList = null;
		try {
			nutAttList = nedmpro0200service.selectNutAtt(vo.getL3Cd());	
		} catch(Exception e) {
			logger.error("getNutAttInfo Error Message : " + e.getMessage());
		}
		
		return nutAttList;
	}
	
	/**
	 * 상품 영양성분 값 가져오기 
	 * FROM JSP : NEDMPRO0201.jsp
	 * @param NEDMPRO0200VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getNutAttInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> getNutAmtInfo(@RequestBody NEDMPRO0200VO vo) throws Exception {
		List<HashMap> nutAttInfoList = null;
		try {
			nutAttInfoList = nedmpro0200service.selectNutAttInfo(vo.getL3Cd());	
		} catch(Exception e) {
			logger.error("getNutAmtInfo Error Message : " + e.getMessage());
		}

		return nutAttInfoList;
	}
	
	/**
	 * 상품 영양성분 저장된 값 가져오기
	 * FROM JSP : NEDMPRO0201.jsp
	 * @param NEDMPRO0200VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getNutAttAmtSaved.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> getNutAttAmtSaved(@RequestBody NEDMPRO0200VO vo) throws Exception {
		List<HashMap> nutAttSavedList = null;
		try {
			nutAttSavedList = nedmpro0200service.selectNutAmtSaved(vo.getProdCd());	
		} catch(Exception e) {
			logger.error("getNutAttAmtSaved Error Message : " + e.getMessage());
		}

		return nutAttSavedList;
	}
	
	/**
	 * 상품 영양성분 저장
	 * FROM JSP : NEDMPRO0201.jsp
	 * @param NEDMPRO0200VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/saveNutAttInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> saveNutAttInfo(@RequestBody NEDMPRO0200VO vo) throws Exception {
		String msg = null;
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		try {
		  msg = nedmpro0200service.mergeNutAmtTmp(vo);	
		} catch(Exception e) {
			logger.error("saveNutAttInfo Error Message : " + e.getMessage());
			
		}
		
		resultMap.put("rtnMsg",msg);
		return resultMap;
	}
	
	/**
	 * 상품 영양성분값 저장 상태값 업데이트
	 * @param NEDMPRO0200VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateNutModifyStatus.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> changeNutAttModStat(@RequestBody NEDMPRO0200VO vo) throws Exception {
		String msg = null;
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		try {
		  msg = nedmpro0200service.updateNutModifyStatus(vo);	
		} catch(Exception e) {
			logger.error("changeNutAttModStat Error Message : " + e.getMessage());
		}
		
		resultMap.put("rtnMsg",msg);
		return resultMap;
	}
	
	/**
	 * 상품 영양성분값 ECO 전송
	 * @param NEDMPRO0200VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/submitNutAttInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> submitNutAttInfo(@RequestBody NEDMPRO0200VO vo) throws Exception {
		String msg = null;
		Integer cnt = 0;
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		try {
			cnt = nedmpro0200service.selectCntNutReq(vo);
			
			if ( cnt > 0 ) {
				msg = "ALREADY_REQUEST";
			}
			
			if ( cnt < 1 ) {
				msg = nedmpro0200service.submitNutAmtInfo(vo);
			}
		} catch(Exception e) {
			logger.error("submitNutAttInfo Error Message : " + e.getMessage());
		}
		
		resultMap.put("rtnMsg",msg);
		return resultMap;
	}
		
}
 