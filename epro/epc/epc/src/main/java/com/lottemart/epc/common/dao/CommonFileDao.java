package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

/**
 * 
 * @Class Name : CommonFileDao.java
 * @Description : 게시판 공통 CommonFileDao
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 11. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("commonFileDao")
public class CommonFileDao extends AbstractDAO
{
	/**
	 * Desc : 게시판 SEQ 가져오기
	 * @Method Name : selectBoardSeq
	 * @param 
	 * @throws SQLException
	 * @return boardSeq
	 */
	public String selectBoardSeq() throws SQLException
	{
		return (String)getSqlMapClientTemplate().queryForObject("file.selectBoardSeq");
	}
	
	/**
	 * Desc : 게시판 최대 DEPTH 가져오기
	 * @Method Name : selectBoardSeq
	 * @param 
	 * @throws SQLException
	 * @return boardSeq
	 */
	public String selectMaxDepth(String boardSeq) throws SQLException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);	
		
		return (String)getSqlMapClientTemplate().queryForObject("file.selectMaxDepth",paramMap);
	}
	
	/**
	 * Desc : 게시판 리스트 SEQ 가져오기
	 * @Method Name : selectListSeq
	 * @param  boardSeq
	 * @throws SQLException
	 * @return listSeq
	 */
	public String selectListSeq(String boardSeq) throws SQLException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);	
		
		return (String)getSqlMapClientTemplate().queryForObject("file.selectListSeq",paramMap);
	}
	
	/**
	 * Desc : 게시판 파일 SEQ 가져오기
	 * @Method Name : selectFileSeq
	 * @param  boardSeq
	 * @throws SQLException
	 * @return fileSeq
	 */
	public String selectFileSeq(String boardSeq) throws SQLException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);	
		
		return (String)getSqlMapClientTemplate().queryForObject("file.selectFileSeq",paramMap);
	}
	
	/**
	 * Desc : 게시판 searchKeyWordSeq 가져오기
	 * @Method Name : selectSearchKeyWordSeq
	 * @param  
	 * @throws SQLException
	 * @return SearchKeyWordSeq
	 */
	public String selectSearchKeyWordSeq() throws SQLException
	{
		
		return (String)getSqlMapClientTemplate().queryForObject("file.selectSearchKeyWordSeq");
	}
	
	/**
	 * Desc : 게시판 fileId 업데이트
	 * @Method Name : updateFileId
	 * @param  boardSeq
	 * @throws SQLException
	 * @return 결과수
	 */
	public int updateFileId(String boardSeq) throws SQLException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);	
		
		return getSqlMapClientTemplate().update("file.updateFileId",boardSeq);
	}
}
