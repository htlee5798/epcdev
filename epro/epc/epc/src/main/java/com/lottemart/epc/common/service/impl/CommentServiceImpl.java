/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.CommentDao;
import com.lottemart.epc.common.model.CommentVO;
import com.lottemart.epc.common.service.CommentService;

/**
 * @Class Name : CommentServiceImpl
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 6:02:21 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("CommentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	/**
	 * Desc : 코멘트 조회 메소드
	 * @Method Name : selectCommentList
	 * @param commentVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectCommentList(CommentVO commentVO) throws Exception {
		return commentDao.selectCommentList(commentVO);
	}

	/**
	 * Desc : 코멘트 등록 메소드
	 * @Method Name : insertComment
	 * @param commentVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertComment(CommentVO commentVO) throws Exception {
		commentDao.insertComment(commentVO);
	}

	/**
	 * Desc : 코멘트 삭제 메소드
	 * @Method Name : deleteComment
	 * @param commentVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteComment(CommentVO commentVO) throws Exception {
		commentDao.deleteComment(commentVO);
	}
}
