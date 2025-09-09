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
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0170VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0170Service;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0170Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0170Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private NEDMPRO0170Service nedmpro0170Service;
	
	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0170.do")
	public String NEDMPRO0170Init(ModelMap model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨. 
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("currDate", nedmpro0170Service.selectCurrDate());
		return "/edi/product/NEDMPRO0170";
	}
	
	/**
	 * 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0170Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProductList(@RequestBody NEDMPRO0170VO vo, HttpServletRequest request){
		Map<String, Object>	map = new HashMap<String, Object>();
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setEntpCds(epcLoginVO.getVendorId());
		map.put("loginId",epcLoginVO.getAdminId());
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());
		
		map.put("paginationInfo", paginationInfo);
		
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		// List Total Count\
		try {
			int totCnt = nedmpro0170Service.selectDisconProductCount(vo);
			paginationInfo.setTotalRecordCount(totCnt);
			map.put("disconProdCnt",totCnt);
			
			// List 가져오기
			List<NEDMPRO0170VO>	resultList 	= 	nedmpro0170Service.selectProductList(vo);
			map.put("resultList", resultList);
	
			// 화면에 보여줄 게시물 리스트
			map.put("pageIdx", vo.getPageIndex());
		
		}catch(Exception e) {
			logger.error("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq Exception ===" + e.toString());
		}
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
	 * 단품정보 입력
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertDisconProduct.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertPbProduct(@RequestBody NEDMPRO0170VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		if(vo.getRegNm()==null) {
			vo.setRegNm(workId);
		}else {
			vo.setModNm(workId);			
		}
		
		
//		if(vo.getRegNm()==null) {
//			vo.setRegNm(epcLoginVO.getCono()[0]);
//		}
//		else {
//			vo.setModNm(epcLoginVO.getCono()[0]);
//		}
		
		String retValue = nedmpro0170Service.insertDisconProduct(vo);
		resultMap.put("retMsg", retValue);
		
		return resultMap;
	}
	
	/**
	 * 단품정보 삭제
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/deleteDisconProduct.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> deletePbProduct(@RequestBody NEDMPRO0170VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		
		String retValue = nedmpro0170Service.deleteDisconProduct(vo);
		resultMap.put("retMsg", retValue);
		
		return resultMap;
	}
	
	/**
	 * 단품정보 등록 조회 (날짜기반)
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectDisconProductByDate.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectDisconProductListByDate(@RequestBody NEDMPRO0170VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setEntpCds(epcLoginVO.getVendorId());
		map.put("loginId",epcLoginVO.getAdminId());
		
		// List 가져오기
		List<NEDMPRO0170VO>	resultList 	= 	nedmpro0170Service.selectDisconProductListByDate(vo);
		map.put("resultList", resultList);
		
		return map;
	}
	
	/**
	 * 단품정보 등록 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectDisconProduct.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectDisconProductList(@RequestBody NEDMPRO0170VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setEntpCds(epcLoginVO.getVendorId());
		
		map.put("loginId",epcLoginVO.getAdminId());
		
		// List 가져오기
		List<NEDMPRO0170VO>	resultList 	= 	nedmpro0170Service.selectDisconProductList(vo);
		map.put("resultList", resultList);

		return map;
	}
}
