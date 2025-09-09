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
import com.lottemart.epc.edi.product.service.NEDMPRO0150Service;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0150Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0150Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private NEDMPRO0150Service nedmpro0150Service;
	
	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0150.do")
	public String NEDMPRO0150Init(ModelMap model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨. 
		model.addAttribute("epcLoginVO", epcLoginVO);
		
		// 현재일자
		model.addAttribute("currDate", nedmpro0150Service.selectCurrDate());
		
		return "/edi/product/NEDMPRO0150";
	}
	
	/**
	 * 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0150Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectPbProductList(@RequestBody NEDMPRO0150VO vo, HttpServletRequest request) throws Exception {
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
		int totCnt = nedmpro0150Service.selectPbProductCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<NEDMPRO0150VO>	resultList 	= 	nedmpro0150Service.selectPbProductList(vo);
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
	
	/**
	 * 재고등록
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertPbProduct.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertPbProduct(@RequestBody NEDMPRO0150VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		
		String retValue = nedmpro0150Service.insertPbProduct(vo, request);
		resultMap.put("retMsg", retValue);
		
		return resultMap;
	}
	
}
