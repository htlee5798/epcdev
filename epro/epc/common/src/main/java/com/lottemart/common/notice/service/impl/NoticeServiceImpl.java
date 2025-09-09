/**
 * 
 */
package com.lottemart.common.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.notice.dao.NoticeDao;
import com.lottemart.common.notice.model.NoticeVO;
import com.lottemart.common.notice.service.NoticeService;
import com.lottemart.common.util.DataMap;




@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	public List<DataMap> mainNoticeList(Map<String, String> paramMap) throws Exception {
		return noticeDao.mainNoticeList(paramMap);
	}
	
	/**
	 * Desc : 공지사항 게시판 점포 리스트
	 * @Method Name : selectAllStoreList
	 * @param boardSeq
	 * @throws Exception
	 * @return List<PBOMBRD0002VO>
	 */
	public List<NoticeVO> selectAllStoreList(String boardSeq) throws Exception {
		return noticeDao.selectAllStoreList(boardSeq);
	}
	
	/**
	 * Desc : 공지사항 게시판 상세 정보
	 * @Method Name : selectNoticeView
	 * @param boardSeq
	 * @throws Exception
	 * @return PBOMBRD0002VO
	 */
	public NoticeVO selectNoticeView(String boardSeq) throws Exception {
		noticeDao.updateViewCnt(boardSeq);
		return noticeDao.selectNoticeView(boardSeq);
	}
	
	public List<NoticeVO> selectPopupNoticeList(Map<String, String> paramMap) throws Exception {
		return noticeDao.selectPopupNoticeList(paramMap);
	}
}
