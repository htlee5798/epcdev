package com.lottemart.common.notice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lottemart.common.notice.model.NoticeVO;
import com.lottemart.common.util.DataMap;

public interface NoticeService {
	
	/**
	 * Desc : 공지사항 
	 * @Method Name : mainNoticeList
	 * @param 
	 * @throws Exception
	 * @return List<NoticeVO>
	 */
	public List<DataMap> mainNoticeList(Map<String, String> paramMap) throws Exception;
    
	/**
	 * Desc : 공지사항 게시판 점포 리스트
	 * @Method Name : selectAllStoreList
	 * @param boardSeq
	 * @throws Exception
	 * @return List<NoticeVO>
	 */
	public List<NoticeVO> selectAllStoreList(String boardSeq) throws Exception; 
	
	/**
	 * Desc : 공지사항 게시판 상세 정보
	 * @Method Name : selectNoticeView
	 * @param boardSeq
	 * @throws Exception
	 * @return NoticeVO
	 */
	public NoticeVO selectNoticeView(String boardSeq) throws Exception;
	
	/**
	 * 팝업공지 목록 조회
	 * @see selectPopupNoticeList
	 * @Locaton    : com.lottemart.common.notice.service
	 * @MethodName  : selectPopupNoticeList
	 * @author     : jyLim
	 * @Description : 
	 * @param strCd, sysDivnCd
	 * @return
	 * @throws Exception
	 */
	public List<NoticeVO> selectPopupNoticeList(Map<String, String> paramMap) throws Exception;
	
}
