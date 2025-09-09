package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.srm.dao.SRMVEN0050Dao;
import com.lottemart.epc.edi.srm.model.SRMVEN005001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0050VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0050Service;

import lcn.module.common.paging.PaginationInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * SRM 모니터링 ServiceImpl
 *
 * @author LEE SANG GU
 * @since 2018.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2018.11.20  	LEE SANG GU		 	최초 생성
 *
 * </pre>
 */
@Service("srmven0050Service")
public class SRMVEN0050ServiceImpl implements SRMVEN0050Service {

	@Autowired
	private SRMVEN0050Dao srmven0050Dao;

	private static final Logger logger = LoggerFactory.getLogger(SRMVEN0050ServiceImpl.class);


	/**
	 * SRM 성과평가 LIST
	 * @param SRMVEN0050VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> selectSrmEvalRes(SRMVEN0050VO vo, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> result = new HashMap<String,Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());

		result.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex()+1);
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());


		// List Total Count
		int totCnt = srmven0050Dao.selectSrmEvalResCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<SRMVEN0050VO>	resultList 	= 	srmven0050Dao.selectSrmEvalRes(vo);
		result.put("listData", resultList);

		// 화면에 보여줄 게시물 리스트
		result.put("pageIdx", vo.getPageIndex());

		try {
			result.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}

		return result;
	}


	/**
	 * SRM 성과평가 상세결과 팝업창 LIST
	 * @param SRMVEN005001VO
	 * @param HttpServletRequest
	 * @return List<SRMVEN005001VO>
	 * @throws Exception
	 */
	@Override
	public List<SRMVEN005001VO> selectSrmEvalResDetailPopup(SRMVEN005001VO vo, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return srmven0050Dao.selectSrmEvalResDetailPopup(vo);
	}
}
