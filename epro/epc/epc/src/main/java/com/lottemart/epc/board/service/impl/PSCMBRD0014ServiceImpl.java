package com.lottemart.epc.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.board.dao.PSCMBRD0014Dao;
import com.lottemart.epc.board.model.PSCMBRD0014TemVO;
import com.lottemart.epc.board.model.PSCMBRD0014VO;
import com.lottemart.epc.board.service.PSCMBRD0014Service;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.common.util.EPCUtil;

/**
 * @Class Name : PSCMBRD0014ServiceImpl.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 4. 29. 오후 7:54:48 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("PSCMBRD0014Service")
public class PSCMBRD0014ServiceImpl implements PSCMBRD0014Service {
	
	private static final Logger logger = LoggerFactory.getLogger(PSCMBRD0014ServiceImpl.class);
	
	@Autowired
	private PSCMBRD0014Dao pscmbrd0014Dao;

	@Autowired
	private CommonDao commonDao;

	
	// 상품 Q&A 게시판 검색
	@Override
	public List<PSCMBRD0014VO> selectQnaSearch(Map<String, String> paramMap)
			throws Exception {
		return pscmbrd0014Dao.selectQnaSearch(paramMap);
	}

	// 상품 Q&A 게시판 총건수
	@Override
	public int selectQnaTotalCnt(Map<String, String> paramMap) throws Exception {
		return pscmbrd0014Dao.selectQnaTotalCnt(paramMap);
	}

	// 상품 Q&A 엑셀다운로드
	@Override
	public List<Map<Object, Object>> selectPscmbrd0014Export(
			Map<Object, Object> paramMap) throws Exception {
		return pscmbrd0014Dao.selectPscmbrd0014Export(paramMap);
	}

	// 상품 Q&A 상세보기
	@Override
	public PSCMBRD0014VO selectQnaView(String recommSeq) throws Exception {
		EPCUtil util = new EPCUtil();
		PSCMBRD0014VO vo = pscmbrd0014Dao.selectQnaView(recommSeq);
		vo.setTitle(util.convertHtmlchars(vo.getTitle()));
		return vo;
	}

	// 답변 달기
	@Override
	public int qnaAnsUpdate(PSCMBRD0014VO pscmbrd0014vo) throws Exception {
		int resultCnt = 0;
		try {
			resultCnt = pscmbrd0014Dao.qnaAnsUpdate(pscmbrd0014vo);
		} catch (Exception e) {
			return 0;
		}
		if(resultCnt>0) {
			commonDao.commit();
			
			// API 연동 (EPC -> 통합BO API)
			try {
				
				PSCMBRD0014VO vo =selectQnaView(pscmbrd0014vo.getProdQnaSeq());//업데이트할 정보에 prodcd가 없어서 다시한번 호출

				if(commonDao.selectEcApprovedYn(vo.getProdCd())){
					
					RestAPIUtil rest = new RestAPIUtil();
					String result = rest.sendRestCall(RestConst.PD_API_0013+pscmbrd0014vo.getProdQnaSeq(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
					logger.debug("API CALL RESULT = " + result);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}	
		}
		
		return resultCnt;
	}

	// 템플릿 저장
	@Override
	public int temAdd(PSCMBRD0014TemVO pscmbrd0014TemVO) throws Exception {
		int resultCnt = 0;
		try {
			resultCnt = pscmbrd0014Dao.temAdd(pscmbrd0014TemVO);
		} catch (Exception e) {
			return 0;
		}
		return resultCnt;
	}

	// 콤보박스 조회
	@Override
	public List<PSCMBRD0014TemVO> temComList() throws Exception {
		return pscmbrd0014Dao.temComList();
	}

	// 템플릿 조회
	@Override
	public PSCMBRD0014TemVO selectComBox(Map<String, String> paramMap)
			throws Exception {
		return pscmbrd0014Dao.selectComBox(paramMap);
	}

	// 템플릿 삭제
	@Override
	public int temDelete(PSCMBRD0014TemVO pscmbrd0014TemVO) throws Exception {
		int resultCnt = 0;
		try {
			resultCnt = pscmbrd0014Dao.temDelete(pscmbrd0014TemVO);
		} catch (Exception e) {
			return 0;
		}
		return resultCnt;
	}
}
