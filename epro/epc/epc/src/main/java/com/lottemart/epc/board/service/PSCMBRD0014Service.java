/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 05. 31. 오후 2:30:50
 * @author      : choi
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0014TemVO;
import com.lottemart.epc.board.model.PSCMBRD0014VO;

/**
 * @Class Name : PSCPBRD0014Service.java
 * @Description : 상품 Q&A 조회 Service 클래스
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 4. 29. 오후 4:40:55  choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCMBRD0014Service {

	/**
	 * Desc : 상품 Q&A 리스트 가져오기
	 * 
	 * @Method Name : selectProductSearch
	 * @param paramMap
	 * @throws Exception
	 * @return List<PSCMBRD0014VO>
	 */
	public List<PSCMBRD0014VO> selectQnaSearch(Map<String, String> paramMap)
			throws Exception;

	/**
	 * Desc : 상품 Q&A 게시판 총건수
	 * 
	 * @Method Name : selectProductTotalCnt
	 * @param paramMap
	 * @throws Exception
	 * @return int
	 */
	public int selectQnaTotalCnt(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 상품 Q&A 리스트 엑셀다운로드
	 * 
	 * @Method Name : selectPscmbrd0014Export
	 * @param paramMap
	 * @throws Exception
	 * @return List<Map<Object,Object>>
	 */
	public List<Map<Object, Object>> selectPscmbrd0014Export(
			Map<Object, Object> paramMap) throws Exception;

	/**
	 * Desc : 상품 Q&A 게시판 상세정보
	 * 
	 * @Method Name : selectQnaView
	 * @param recommSeq
	 * @return
	 * @throws Exception
	 * @return PSCMBRD0014VO
	 */
	public PSCMBRD0014VO selectQnaView(String recommSeq) throws Exception;

	/**
	 * Desc : 상품 Q&A 답변 달기
	 * 
	 * @Method Name : qnaAnsUpdate
	 * @param recommSeq
	 * @param ansContent
	 * @return
	 * @throws Exception
	 * @return PSCMBRD0014VO
	 */
	public int qnaAnsUpdate(PSCMBRD0014VO pscmbrd0014vo) throws Exception;

	/**
	 * Desc : 템플릿 저장
	 * @Method Name : TemAdd
	 @param name
	 @return
	 @throws Exception
	 * @return  int
	 */
	public int temAdd(PSCMBRD0014TemVO pscmbrd0014TemVO) throws Exception;
	
	/**
	 * Desc : 템플릿 삭제
	 * @Method Name : temDelete
	 @param pscmbrd0014TemVO
	 @return
	 @throws Exception
	 * @return  int
	 */
	public int temDelete(PSCMBRD0014TemVO pscmbrd0014TemVO) throws Exception;

	
	/**
	 * Desc : 콤보박스 조회
	 * @Method Name : temComList
	 @return
	 @throws Exception
	 * @return  List<DataMap>
	 */
	public List<PSCMBRD0014TemVO> temComList() throws Exception;
	
	/**
	 * Desc : 템플릿 내용 조회
	 * @Method Name : selectComBox
	 @param recommSeq
	 @return
	 @throws Exception
	 * @return  PSCMBRD0014TemVO
	 */
	public PSCMBRD0014TemVO selectComBox(Map<String, String> paramMap) throws Exception;

}
