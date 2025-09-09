/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 19. 오후 2:38:50
 * @author      : wcpark
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;

/**
 * @Class Name : PSCMBRD0001Dao
 * @Description : 공지사항 목록 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:39:01 wcpark
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMBRD0001Dao")
public class PSCMBRD0001Dao extends AbstractDAO {


	/**
	 * Desc : 공지사항 목록을 조회하는 메소드
	 * @Method Name : selectBoardList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPBRD0002SearchVO> getList(DataMap paramMap) throws SQLException{
		return (List<PSCPBRD0002SearchVO>)getSqlMapClientTemplate().queryForList("PSCMBRD0001.selectBoardList", paramMap);
	}

}
