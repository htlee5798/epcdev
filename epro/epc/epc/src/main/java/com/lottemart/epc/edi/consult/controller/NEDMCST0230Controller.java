package com.lottemart.epc.edi.consult.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.lottemart.epc.edi.consult.model.NEDMCST0230VO;
import com.lottemart.epc.edi.consult.service.NEDMCST0230Service;



@Controller
public class NEDMCST0230Controller {
	private static final Logger logger = LoggerFactory.getLogger(NEDMCST0230Controller.class);

	@Autowired
	private NEDMCST0230Service nedmcst0230Service;


	@Resource(name = "configurationService")
	private ConfigurationService config;


    //AS 조회 첫페이지
	@RequestMapping(value = "/edi/consult/NEDMCST0230.do", method = RequestMethod.GET)
	public String asMain(Locale locale,  ModelMap model) {

		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		map.put("srchStartDate", nowDate);
		map.put("srchEndDate", nowDate);

		model.addAttribute("paramMap",map);


		return "/edi/consult/NEDMCST0230";
	}

	//AS 조회
	@RequestMapping(value = "/edi/consult/NEDMCST0230Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> asMainSelect(@RequestBody NEDMCST0230VO nEDMCST0230VO, HttpServletRequest request) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMCST0230VO.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();


		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(nEDMCST0230VO.getPageIndex());
		paginationInfo.setRecordCountPerPage(nEDMCST0230VO.getRecordCountPerPage());
		paginationInfo.setPageSize(nEDMCST0230VO.getPageSize());

		resultMap.put("paginationInfo", paginationInfo);

		nEDMCST0230VO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		nEDMCST0230VO.setLastIndex(paginationInfo.getLastRecordIndex());
		nEDMCST0230VO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());


		// List Total Count
		int totCnt = nedmcst0230Service.asMainSelectCount(nEDMCST0230VO);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<NEDMCST0230VO> asList = nedmcst0230Service.asMainSelect(nEDMCST0230VO);
		resultMap.put("asList", asList);

		// 화면에 보여줄 게시물 리스트
		resultMap.put("pageIdx", nEDMCST0230VO.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			resultMap.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return resultMap;
	}

	//AS 수정
	@RequestMapping(value = "/edi/consult/NEDMCST0230Update.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> asMainUpdate(@RequestBody NEDMCST0230VO nEDMCST0230VO, HttpServletRequest request) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		nEDMCST0230VO.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int cnt = nedmcst0230Service.asMainUpdate(nEDMCST0230VO);

		resultMap.put("cnt", cnt);

		return resultMap;
	}


}

