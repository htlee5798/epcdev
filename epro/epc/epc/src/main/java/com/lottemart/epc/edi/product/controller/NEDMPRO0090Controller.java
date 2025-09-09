package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
import com.lottemart.epc.edi.product.model.NEDMPRO0080VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0080Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0090Service;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0090Controller {
	
	@Resource (name = "configurationService")
	private ConfigurationService config;

	@Autowired
	NEDMPRO0090Service nedmpro0090service;
	
	@Autowired
	NEDMPRO0080Service nedmpro0080service;
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0090Controller.class);
	
	/**
	 * 상품 분석속성 요청조회 페이지
	 * @param ModelMap
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0090.do")
	public String showPageRegProdGrpAttrInfo(ModelMap model, HttpServletRequest request) throws Exception {
		
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
		return "/edi/product/NEDMPRO0090";
	}
	
	/**
	 * 상품 분석속성등록 페이지
	 * @param NEDMPRO0090VO
	 * @param ModelMap
	 * @param HttpServletRequest
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0091.do")
	public String selectAProdGrpAttrInfo(NEDMPRO0090VO vo, ModelMap model, HttpServletRequest request) throws Exception {		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		Map<String, String> prodInfoSelected = new HashMap<String, String>();
		prodInfoSelected.put("prodCd" , vo.getProdCd());
		prodInfoSelected.put("l3Cd"   , vo.getL3Cd());
		prodInfoSelected.put("msg"    , vo.getMsg());
		
		int seqAttr = nedmpro0090service.getMaxSeqAttr(vo.getProdCd());
		vo.setSeq(String.valueOf(seqAttr));
		int cntNotResponseAttr = nedmpro0090service.getCntNotResponseAttr(vo);
			
		model.addAttribute("epcLoginVO"         , epcLoginVO);
		model.addAttribute("prodInfo"           , prodInfoSelected);
		model.addAttribute("cntNotResponseAttr" , cntNotResponseAttr);
		model.addAttribute("seqAttr"            , seqAttr);
		return "/edi/product/NEDMPRO0091";
	}
    
	/**
	 * 상품 분석속성 가져오기 
	 * FROM JSP : NEDMPRO0091.jsp
	 * @param NEDMPRO0090VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getGrpAttrInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> getGrpAttrInfo(@RequestBody NEDMPRO0090VO vo) throws Exception {
		List<HashMap> grpAttrInfoList = null;
		try {
			grpAttrInfoList = nedmpro0090service.selectGrpAtt(vo.getL3Cd());	
		} catch(Exception e) {
			logger.error("getGrpAttrInfo Error Message : " + e.getMessage());
		}
		
		return grpAttrInfoList;
	}
	
	/**
	 * 상품 분석속성 값 가져오기 
	 * FROM JSP : NEDMPRO0091.jsp
	 * @param NEDMPRO0090VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getGrpAttrOptInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> getGrpAttrOptInfo(@RequestBody NEDMPRO0090VO vo) throws Exception {
		List<HashMap> grpAttrOptInfoList = null;
		try {
			grpAttrOptInfoList = nedmpro0090service.selectGrpAttOpt(vo.getL3Cd());	
		} catch(Exception e) {
			logger.error("getGrpAttrOptInfo Error Message : " + e.getMessage());
		}

		return grpAttrOptInfoList;
	}
	
	/**
	 * 상품 분석속성 저장된 값 가져오기
	 * FROM JSP : NEDMPRO0091.jsp
	 * @param NEDMPRO0090VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/getGrpAttrSelectedOptInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashMap> getGrpAttrSelectedOptInfo(@RequestBody NEDMPRO0090VO vo) throws Exception {
		List<HashMap> grpAttrSelectedOptInfoList = null;
		try {
			grpAttrSelectedOptInfoList = nedmpro0090service.selectGrpAttSelectedOpt(vo.getProdCd());	
		} catch(Exception e) {
			logger.error("getGrpAttrSelectedOptInfo Error Message : " + e.getMessage());
		}

		return grpAttrSelectedOptInfoList;
	}
	
	/**
	 * 상품 분석속성값 저장
	 * FROM JSP : NEDMPRO0091.jsp
	 * @param NEDMPRO0090VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/saveGrpAttrOptInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> saveGrpAttrOptInfo(@RequestBody NEDMPRO0090VO vo) throws Exception {
		String msg = null;
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		try {
		  msg = nedmpro0090service.saveGrpAttOptInfoTmp(vo);	
		} catch(Exception e) {
			logger.error("saveGrpAttrOptInfo Error Message : " + e.getMessage());
			
		}
		
		resultMap.put("rtnMsg",msg);
		return resultMap;
	}
	
	/**
	 * 상품 분석속성값 저장 상태값 업데이트 (확정취소)
	 * FROM JSP : NEDMPRO0091.jsp
	 * @param NEDMPRO0090VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateModifyStatusAttr.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> changeModifyStatusAttr(@RequestBody NEDMPRO0090VO vo) throws Exception {
		String msg = null;
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		try {
		  msg = nedmpro0090service.updateModifyStatusAttr(vo);	
		} catch(Exception e) {
			logger.error("updateModifyStatusAttr Error Message : " + e.getMessage());
		}
		
		resultMap.put("rtnMsg",msg);
		return resultMap;
	}
	
	/**
	 * 상품 분석속성값 ECO 전송
	 * FROM JSP : NEDMPRO0091.jsp
	 * @param NEDMPRO0090VO
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/submitGrpAttrOptInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> submitGrpAttrOptInfo(@RequestBody NEDMPRO0090VO vo) throws Exception {
		String msg = null;
		Integer cnt = 0;
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		try {
	      cnt = nedmpro0090service.selectCntGrpReq(vo);
			
	      if ( cnt > 0 ) {
	    	  msg = "ALREADY_REQUEST";
	      }
	      
	      if ( cnt < 1 ) {
	    	  msg = nedmpro0090service.submitGrpAttrOptInfo(vo);    
	      }
		} catch(Exception e) {
			logger.error("submitGrpAttrOptInfo Error Message : " + e.getMessage());
		}
		
		resultMap.put("rtnMsg", msg);
		return resultMap;
	}
		
}
 