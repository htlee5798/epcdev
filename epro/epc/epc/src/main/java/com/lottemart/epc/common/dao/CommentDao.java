/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.CommentVO;

/**
 * @Class Name : CommentDao
 * @Description : 코멘트 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 6:02:15 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("CommentDao")
public class CommentDao extends AbstractDAO {

	/**
	 * Desc : 코멘트 조회 메소드
	 * @Method Name : selectCommentList
	 * @param commentVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked") 
	public List<DataMap> selectCommentList(CommentVO commentVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("Comment.selectCommentList", commentVO);
	}
	
	/**
	 * Desc : 코멘트 등록 메소드
	 * @Method Name : insertComment
	 * @param commentVO
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertComment(CommentVO commentVO) throws SQLException{
		update("Comment.insertComment", commentVO);
	}
	
	/**
	 * Desc : 코멘트 삭제 메소드
	 * @Method Name : deleteComment
	 * @param commentVO
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteComment(CommentVO commentVO) throws SQLException{
		delete("Comment.deleteComment", commentVO);
	}
}
