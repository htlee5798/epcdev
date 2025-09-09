/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2016. 05. 02. 오후 2:38:50
 * @author      : kslee 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0014TemVO;
import com.lottemart.epc.board.model.PSCMBRD0014VO;

/**
 * @Class Name : PSCMBRD0014Dao.java
 * @Description : 상품 Q&A 목록 Dao 클래스
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 4. 29. 오후 5:16:35 choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("PSCMBRD0014Dao")
public class PSCMBRD0014Dao extends AbstractDAO {

	/**
	 * Desc : 상품 Q&A 목록을 조회하는 메소드
	 * 
	 * @Method Name : selectQnaSearch
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @return List<PSCMBRD0014VO>
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMBRD0014VO> selectQnaSearch(Map<String, String> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList( "pscmbrd0014.selectQnaSearch", paramMap);
	}

	/**
	 * Desc : 상품 Q&A 게시판 총건수
	 * 
	 * @Method Name : selectQnaTotalCnt
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @return int
	 */
	public int selectQnaTotalCnt(Map<String, String> paramMap) throws Exception {
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer) getSqlMapClientTemplate().queryForObject( "pscmbrd0014.selectQnaTotalCnt", paramMap);
		return iTotCnt.intValue();
	}
	
	/**
	 * Desc : 상품 Q&A 답변 달기
	 * @Method Name : qnaAnsUpdate
	 @param recommSeq
	 @param ansContent
	 @return
	 @throws Exception
	 * @return  PSCMBRD0014VO
	 */
	public int qnaAnsUpdate(PSCMBRD0014VO pscmbrd0014vo)throws Exception{
		return getSqlMapClientTemplate().update("pscmbrd0014.qnaAnsUpdate",pscmbrd0014vo);
	}
	
	/**
	 * Desc :  템플릿 저장
	 * @Method Name : TemAdd
	 @param name
	 @return
	 @throws Exception
	 * @return  int
	 */
	public int temAdd(PSCMBRD0014TemVO pscmbrd0014TemVO) throws Exception{
		int ret =getSqlMapClientTemplate().update("pscmbrd0014.temAdd", pscmbrd0014TemVO); 
		return  ret;
		
	}
	
	/**
	 * Desc : 템플릿 삭제
	 * @Method Name : temDelete
	 @param pscmbrd0014TemVO
	 @return
	 @throws Exception
	 * @return  int
	 */
	public int temDelete(PSCMBRD0014TemVO pscmbrd0014TemVO) throws Exception{
		int ret =(Integer) getSqlMapClientTemplate().delete("pscmbrd0014.temDelete", pscmbrd0014TemVO); 
		return  ret;
		
	}

	/**
	 Desc : 상품 Q&A 상세정보
	 @Method Name : selectQnaView
	 @param recommSeq
	 @throws SQLException
	 @return  PSCMBRD0014VO
	 */
	public PSCMBRD0014VO selectQnaView(String recommSeq) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("recommSeq", recommSeq);
		return (PSCMBRD0014VO)getSqlMapClientTemplate().queryForObject("pscmbrd0014.selectQnaView",paramMap);
	}
	
	/**
	 * Desc :  상품 Q&A 리스트 엑셀다운로드
	 @Method Name : selectPscmbrd0013Export
	 @param paramMap
	 @throws Exception
	 @return  List<Map<Object,Object>>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> selectPscmbrd0014Export(Map<Object, Object> paramMap) throws Exception {
		return (List<Map<Object, Object>>)getSqlMapClientTemplate().queryForList("pscmbrd0014.selectPscmbrd0014Export",paramMap);
	}
	


			
	/**
	 * Desc : 콤보 박스 조회
	 * @Method Name : temComList
	 @return
	 @throws Exception
	 * @return  List<DataMap>
	 */
	public List<PSCMBRD0014TemVO> temComList() throws Exception{
		return getSqlMapClientTemplate().queryForList("pscmbrd0014.temComList");
	}
	
	/**
	 * Desc : 템플릿 내용 조회
	 * @Method Name : selectComBox
	 @param recommSeq
	 @return
	 @throws Exception
	 * @return  PSCMBRD0014TemVO
	 */
	public PSCMBRD0014TemVO selectComBox(Map<String, String> paramMap) throws Exception{
		return  (PSCMBRD0014TemVO) getSqlMapClientTemplate().queryForObject("pscmbrd0014.selectComBox", paramMap);
	}
	
	
	
}
