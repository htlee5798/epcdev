/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2016. 01. 19. 
 * @author      : projectBOS32 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0012VO;

/**
 * @Class Name : PSCPBRD0012Dao
 * @Description : 고객센터문의사항 상세 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:00:01 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCPBRD0012Dao")
public class PSCPBRD0012Dao extends AbstractDAO{

	/**
	 * Desc : 고객센터문의사항 상세(원문) 정보를 조회하는 메소드
	 * @Method Name : selectCcQnaDetailPopup
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0012VO selectCcQnaDetailPopup(PSCPBRD0012VO vo) throws SQLException{
		return (PSCPBRD0012VO) selectByPk("PSCPBRD0012.selectCcQnaDetailPopup", vo);
	}
	
	/**
	 * Desc : 고객센터문의사항 상세 정보를 조회하는 메소드
	 * @Method Name : selectCcQnaDetailPopup
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCPBRD0012VO> selectCcQnaDetailList(PSCPBRD0012VO vo) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCPBRD0012.selectCcQnaDetailList",vo);
	}
	
	/**
	 * 게시판 등록전 seq 생성.
	 * Desc : 
	 * @Method Name : selectBoardSeq
	 * @param 
	 * @return DataMap
	 * @throws SQLException
	 */
	public DataMap selectBoardSeq() throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCPBRD0012.selectBoardSeq");
	}
	
	/**
	 * 답변 등록전 List Seq 생성.
	 * Desc : 
	 * @Method Name : selectBoardListlSeq
	 * @param 
	 * @return DataMap
	 * @throws SQLException
	 */
	public DataMap selectBoardListlSeq(String boardSeq) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCPBRD0012.selectBoardListlSeq", boardSeq);
	}
	
	/**
	 * Desc : 고객센터문의사항 답변 정보를 등록하는 메소드
	 * @Method Name : insertReCcQnaDetailPopup
	 * @param vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertReCcQnaDetailPopup(PSCPBRD0012VO vo) throws SQLException{
		insert("PSCPBRD0012.insertReCcQnaDetailPopup", vo);
	}
	
	/**
	 * Desc : 고객센터문의사항 상세 정보를 수정하는 메소드
	 * @Method Name : updateCcQnaDetailPopup
	 * @param vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaDetailPopup(PSCPBRD0012VO vo) throws SQLException{
		update("PSCPBRD0012.updateCcQnaDetailPopup", vo);
	}

	/**
	 * Desc : 고객센터문의사항 상세 정보를 수정시 댓글도 삭제여부 처리하는 메소드
	 * @Method Name : updateCcQnaDetailPopupReply
	 * @param vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaDetailPopupReply(PSCPBRD0012VO vo) throws SQLException{
		update("PSCPBRD0012.updateCcQnaDetailPopupReply", vo);
	}

	/**
	 * Desc : 고객센터문의사항 상세 조회시 조회수를 증가시키는 메소드
	 * @Method Name : updateCcQnaReadCount
	 * @param vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaReadCount(PSCPBRD0012VO vo) throws SQLException{
		update("PSCPBRD0012.updateCcQnaReadCount", vo);
	}

	/**
	 * Desc : 다운로드 파일 정보를 삭제하는 메소드
	 * @Method Name : deleteAtchFileId
	 * @param vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteAtchFileId(PSCPBRD0012VO vo) throws SQLException{
		update("PSCPBRD0012.deleteAtchFileId", vo);
	}
}
 