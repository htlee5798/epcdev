/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.common.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.CommentVO;

/**
 * @Class Name : CommentService
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 6:02:19 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface CommentService {
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
	public List<DataMap> selectCommentList(CommentVO commentVO) throws Exception;
	
	/**
	 * Desc : 코멘트 등록 메소드
	 * @Method Name : insertComment
	 * @param commentVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertComment(CommentVO commentVO) throws Exception;
	
	/**
	 * Desc : 코멘트 삭제 메소드
	 * @Method Name : deleteComment
	 * @param commentVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteComment(CommentVO commentVO) throws Exception;
}
