package com.lottemart.epc.edi.buy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.lang.RuntimeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.paging.PaginationInfo;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.buy.model.NEDMBUY0060VO;
import com.lottemart.epc.edi.buy.service.NEDMBUY0060Service;
import com.lottemart.epc.edi.commonUtils.commonUtil;

/**
 * @Class Name : NEDMBUY0060Controller
 * @Description : 매입정보 점포상품별 조회 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.17	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Controller
public class NEDMBUY0060Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMBUY0060Controller.class);

	@Autowired
	private NEDMBUY0060Service nedmbuy0060Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;


	@RequestMapping(value = "/edi/buy/NEDMBUY0060.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String day(Locale locale,  ModelMap model,HttpServletRequest request, NEDMBUY0060VO nEDMBUY0060VO) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String ven="";
		for(int i=0;i<epcLoginVO.getVendorId().length;i++){
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven=ven.substring(0, ven.length()-1);
		map.put("ven", ven);


		if(nEDMBUY0060VO.getIsForwarding() != null && "Y".equals(nEDMBUY0060VO.getIsForwarding())){
			map.put("isForwarding",  nEDMBUY0060VO.getIsForwarding());
			map.put("srchStartDate", nEDMBUY0060VO.getSrchStartDate());
			map.put("srchEndDate",   nEDMBUY0060VO.getSrchEndDate());
		}else{
			map.put("srchStartDate", commonUtil.nowDateBack(nowDate));
			map.put("srchEndDate",   commonUtil.nowDateBack(nowDate));
		}

		model.addAttribute("paramMap",map);

		return "/edi/buy/NEDMBUY0060";
	}


	@RequestMapping(value="/edi/buy/NEDMBUY0060Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> newProductList(@RequestBody NEDMBUY0060VO nEDMBUY0060VO, HttpServletRequest request) throws Exception {


		if (nEDMBUY0060VO == null || request == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(nEDMBUY0060VO.getPageIndex());
		paginationInfo.setRecordCountPerPage(Integer.parseInt(nEDMBUY0060VO.getRecordCnt()));
		paginationInfo.setPageSize(nEDMBUY0060VO.getPageSize());

		resultMap.put("paginationInfo", paginationInfo);

		nEDMBUY0060VO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		nEDMBUY0060VO.setLastIndex(paginationInfo.getLastRecordIndex());
		nEDMBUY0060VO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMBUY0060VO.setVenCds(epcLoginVO.getVendorId());

		//-----list total count
		int resultListTotCnt	=	nedmbuy0060Service.selectStoreProductInfoTotCnt(nEDMBUY0060VO);
		paginationInfo.setTotalRecordCount(resultListTotCnt);

		List<NEDMBUY0060VO>	resultList 	= 	nedmbuy0060Service.selectStoreProductInfo(nEDMBUY0060VO);
		resultMap.put("resultList", resultList);

		// 화면에 보여줄 게시물 리스트
		//resultMap.put("pageIdx", nEDMBUY0060VO.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			resultMap.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}

		return resultMap;
	}

	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/buy/NEDMBUY0060Text.do", method=RequestMethod.POST)
    public void createTextOrdProdList(NEDMBUY0060VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(Integer.parseInt(vo.getRecordCnt()));
		paginationInfo.setPageSize(vo.getPageSize());

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		//-----list total count
		int resultListTotCnt	=	nedmbuy0060Service.selectStoreProductInfoTotCnt(vo);
		paginationInfo.setTotalRecordCount(resultListTotCnt);

		nedmbuy0060Service.createTextOrdProdList(vo, request, response);
	}
}
