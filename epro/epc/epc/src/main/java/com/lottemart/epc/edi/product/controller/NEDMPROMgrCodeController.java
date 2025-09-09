package com.lottemart.epc.edi.product.controller;

import java.util.ArrayList;
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
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagDao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
import com.lottemart.epc.edi.product.service.NEDMPROMgrCodeService;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;

@Controller
@RequestMapping("/mngr")
public class NEDMPROMgrCodeController extends BaseController {
		
	private static final Logger logger = LoggerFactory.getLogger(NEDMPROMgrCodeController.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private CustomTagDao customTagDao;
	
	@Autowired
	NEDMPROMgrCodeService nedmproMgrCodeService;
	
	
	/**
	 * 코드관리 화면 init
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/commonMgrCode.do")
	public String mgrCodeInit(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new IllegalArgumentException();
		}
		
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String[] ven_cd = epcLoginVO.getVendorId();

		List<CommonProductVO> venList = new ArrayList<>();
		
		for(int i=0; ven_cd.length >i; i++)
		{
			CommonProductVO venListVo = new CommonProductVO();
			
			venListVo.setVenCd(ven_cd[i]);
			venListVo.setVenNm(ven_cd[i]);

			venList.add(venListVo);
		}
		
		model.addAttribute("vendorList", 		venList);
		model.addAttribute("epcLoginVO", 		epcLoginVO);
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));		//검색종료일
		return "edi/product/NEDMPROMgrCode";
	}
	
	/**
	 * 마스터 코드 조회 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectMgrCodeList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectMgrCodeList(@RequestBody CommonProductVO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCd() == null || vo.getSrchEntpCd().equals("")) {
			map.put("resultList", "");
		}

//		PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(vo.getPageIndex());
//		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
//		paginationInfo.setPageSize(vo.getPageSize());

//		map.put("paginationInfo", paginationInfo);

//		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		vo.setLastIndex(paginationInfo.getLastRecordIndex());
//		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		try {
			int totCnt = nedmproMgrCodeService.selectMgrCodeListCount(vo);
//			paginationInfo.setTotalRecordCount(totCnt);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		// List 가져오기
		List<CommonProductVO>	resultList 	= 	nedmproMgrCodeService.selectMgrCodeList(vo);
		map.put("resultList", resultList);

		// 화면에 보여줄 게시물 리스트
		/*
		map.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		 */
		return map;
	}
	
	/**
	 * 디테일 코드 조회 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectMgrCodeDtlList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectMgrCodeDtlList(@RequestBody CommonProductVO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCd() == null || vo.getSrchEntpCd().equals("")) {
			map.put("resultList", "");
		}

//		PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(vo.getPageIndex());
//		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
//		paginationInfo.setPageSize(vo.getPageSize());

	//	map.put("paginationInfo", paginationInfo);

//		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		vo.setLastIndex(paginationInfo.getLastRecordIndex());
//		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		try {
			int totCnt = nedmproMgrCodeService.selectMgrCodeDtlListCount(vo);
//			paginationInfo.setTotalRecordCount(totCnt);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		// List 가져오기
		List<CommonProductVO>	resultList 	= 	nedmproMgrCodeService.selectMgrCodeDtlList(vo);
		map.put("resultList", resultList);

		// 화면에 보여줄 게시물 리스트
		/*
		map.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		 */
		return map;
	}
	
	
	
	/**
	 * 코드 저장 
	 * @param paramVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertMgrDtlCode.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertMgrDtlCode(@RequestBody CommonProductVO paramVo, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "fail");
		
		try {
			result = nedmproMgrCodeService.insertMgrDtlCode(paramVo, request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 *  코드 삭제 
	 * @param paramVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteMgrDtlCode.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> deleteMgrDtlCode(@RequestBody CommonProductVO paramVo, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "fail");
		
		try {
			result = nedmproMgrCodeService.deleteMgrDtlCode(paramVo, request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	
	
	
}
