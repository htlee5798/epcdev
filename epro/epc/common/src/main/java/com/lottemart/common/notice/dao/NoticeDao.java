package com.lottemart.common.notice.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.notice.model.NoticeVO;
import com.lottemart.common.util.DataMap;


@Repository("NoticeDao")
public class NoticeDao extends AbstractDAO {

//	@Autowired
//	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("unchecked") 
	public List<DataMap> mainNoticeList(Map<String, String> paramMap) throws SQLException{
		//Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("strCd", strCd);		
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("notice.mainNoticeList",paramMap);
	}
	
	/**
	 * Desc : 공지사항 게시판 점포 리스트
	 * @Method Name : selectAllStoreList
	 * @param boardSeq
	 * @throws SQLException
	 * @return List<NoticeVO>
	 */
	public List<NoticeVO> selectAllStoreList(String boardSeq) throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);		
		return getSqlMapClientTemplate().queryForList("notice.selectAllStoreList",paramMap);
	}
	
	/**
	 * Desc : 공지사항 게시판 상세 정보
	 * @Method Name : selectNoticeView
	 * @param boardSeq
	 * @throws SQLException
	 * @return NoticeVO
	 */
	public NoticeVO selectNoticeView(String boardSeq) throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);		
		return (NoticeVO)getSqlMapClientTemplate().queryForObject("notice.selectNoticeView",paramMap);
	}
	
	/**
	 * Desc : 공지사항  게시판 조회수 업데이트
	 * @Method Name : updateViewCnt
	 * @param boardSeq
	 * @throws SQLException
	 * @return 결과수
	 */
	public int updateViewCnt(String boardSeq) throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardSeq", boardSeq);	
		return getSqlMapClientTemplate().update("notice.updateViewCnt", paramMap);	
	}
	
	@SuppressWarnings("unchecked")
	public List<NoticeVO> selectPopupNoticeList(Map<String, String> paramMap) throws SQLException {
		return getSqlMapClientTemplate().queryForList("notice.selectPopupNoticeList", paramMap);
	}	
}
