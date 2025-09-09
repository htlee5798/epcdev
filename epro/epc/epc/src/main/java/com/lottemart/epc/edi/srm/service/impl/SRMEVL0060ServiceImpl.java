package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.edi.srm.dao.SRMEVL0060Dao;
import com.lottemart.epc.edi.srm.model.SRMEVL0060VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMEVL0060Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import com.lottemart.epc.edi.srm.utils.SRMPagingUtils;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 품질경영평가 / 품질경영평가 대상 ServiceImpl
 *
 * @author SHIN SE JIN
 * @since 2016.07.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.11  	SHIN SE JIN		 최초 생성
 *   2016.07.26     LEE HYOUNG TAK   수정
 * </pre>
 */
@Service("SRMEVL0060Service")
public class SRMEVL0060ServiceImpl implements SRMEVL0060Service {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private SRMEVL0060Dao SRMEVL0060Dao;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	/**
	 * 품질평가 list
	 * @param SRMEVL0060VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectQualityEvaluationPeriodicList(SRMEVL0060VO vo, HttpServletRequest request) throws Exception {
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

		//세션에서 로그인한 사용자 업체코드 받아오기
		vo.setEvalSellerCode(srmSessionVO.getEvalSellerCode());
		vo.setEvUserId(srmSessionVO.getEvUserId());

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		Map<String,Object> result = new HashMap<String,Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
//		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setRecordCountPerPage(10);					//페이지 LOW 수
		paginationInfo.setPageSize(vo.getPageSize());

		result.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex()+1);
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = SRMEVL0060Dao.selectQualityEvaluationPeriodicListCount(vo);

		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<SRMEVL0060VO>	resultList 	= 	SRMEVL0060Dao.selectQualityEvaluationPeriodicList(vo);

		result.put("listData", resultList);

		// 화면에 보여줄 게시물 리스트
		result.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		result.put("contents", SRMPagingUtils.makingPagingContents(paginationInfo, "goPage"));

		return result;
	}

	/**
	 * 품질평가 list Excel
	 * @param HttpServletRequest
	 * @param SRMEVL0060VO
	 * @return List<SRMEVL0060VO>
	 * @throws Exception
	 */
	public List<SRMEVL0060VO> selectQualityEvaluationListExcel(SRMEVL0060VO vo, HttpServletRequest request) throws Exception {
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

		//세션에서 로그인한 사용자 업체코드 받아오기
		vo.setEvalSellerCode(srmSessionVO.getEvalSellerCode());
		vo.setEvUserId(srmSessionVO.getEvUserId());

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		return SRMEVL0060Dao.selectQualityEvaluationPeriodicListExcel(vo);
	}


	/**
	 * 파트너사 정보 조회
	 * @param SRMEVL0060VO
	 * @return SRMEVL0060VO
	 * @throws Exception
	 */
	public SRMEVL0060VO selectQualityEvaluationCompInfo(SRMEVL0060VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));
		vo.setEvUserId(srmSessionVO.getEvUserId());

		return SRMEVL0060Dao.selectQualityEvaluationCompInfo(vo);
	}
}
