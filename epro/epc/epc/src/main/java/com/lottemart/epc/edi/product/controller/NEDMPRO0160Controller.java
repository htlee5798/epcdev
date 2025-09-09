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
import com.lottemart.epc.edi.product.model.NEDMPRO0160VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0160Service;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0160Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0160Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private NEDMPRO0160Service nedmpro0160Service;
	
	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0160.do")
	public String NEDMPRO0160Init(ModelMap model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨. 
		model.addAttribute("epcLoginVO", epcLoginVO);
		
		// 현재일자
		//model.addAttribute("currDate", nedmpro0160Service.selectCurrDate());
		
		return "/edi/product/NEDMPRO0160";
	}
	
	/**
	 * 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0160Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectPbProductList(@RequestBody NEDMPRO0160VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();
		
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());
		
		/*if (vo.getSrchEntpCd() == null || vo.getSrchEntpCd().equals("")) {
			map.put("resultList", "");
		}*/
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());
		
		map.put("paginationInfo", paginationInfo);
		
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = nedmpro0160Service.selectPbProductCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<NEDMPRO0160VO>	resultList 	= 	nedmpro0160Service.selectPbProductList(vo);
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
		
		return map;
	}
	
}
