/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2016. 01. 19. 
 * @author      : projectBOS32 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service.impl;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCPBRD0012Dao;
import com.lottemart.epc.board.model.PSCPBRD0012VO;
import com.lottemart.epc.board.service.PSCPBRD0012Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;

/**
 * @Class Name : PSCPBRD0012ServiceImpl
 * @Description : 업체문의사항 상세 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:06:05 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCPBRD0012Service")
public class PSCPBRD0012ServiceImpl implements PSCPBRD0012Service {
	@Autowired
	private PSCPBRD0012Dao pscpbrd0012Dao;

	/**
	 * Desc : 문의사항 상세(원문) 정보를 조회하는 메소드
	 * @Method Name : selectCcQnaDetailPopup
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0012VO selectCcQnaDetailPopup(PSCPBRD0012VO vo, EpcLoginVO epcLoginVO) throws Exception {
		PSCPBRD0012VO map = pscpbrd0012Dao.selectCcQnaDetailPopup(vo);
		String orgBoardSeq = vo.getBoardSeq();
		
		List<String> vendorList = LoginUtil.getVendorList(epcLoginVO);
		vo.setBoardSeq(vo.getNowBoardSeq());
		PSCPBRD0012VO map2 = pscpbrd0012Dao.selectCcQnaDetailPopup(vo);
		
		if(map2 != null){
			//2016.08.24 게시판팝업수정
			//if(!vendorList.contains(map2.getRegId()) && !epcLoginVO.getAdminId().equals(map2.getRegId()) && vendorList != null){
			if(!vendorList.contains(map2.getRegId()) && vendorList != null){
				pscpbrd0012Dao.updateCcQnaReadCount(vo);
			}
		}
		
		vo.setBoardSeq(orgBoardSeq);
		
		return map;
	}
	
	public PSCPBRD0012VO selectCcQnaDetail(PSCPBRD0012VO vo) throws Exception {
		return pscpbrd0012Dao.selectCcQnaDetailPopup(vo);
	}
	
	/**
	 * Desc : 문의사항 상세 정보를 조회하는 메소드
	 * @Method Name : selectCcQnaDetailList
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCPBRD0012VO> selectCcQnaDetailList(PSCPBRD0012VO vo)	throws Exception {
		return pscpbrd0012Dao.selectCcQnaDetailList(vo);
	}
	
	/**
	 * 게시판 등록전 seq 생성.
	 * Desc : 
	 * @Method Name : selectBoardSeq
	 * @param 
	 * @return DataMap
	 * @throws SQLException
	 */
	public DataMap selectBoardSeq() throws Exception {
		return pscpbrd0012Dao.selectBoardSeq();
	}
	
	/**
	 * 답변 등록전 List Seq 얻어 오기.
	 * 
	 * @return DataMap
	 * @throws Exception
	 */
	public DataMap selectBoardListlSeq(String boardSeq) throws Exception {
		return pscpbrd0012Dao.selectBoardListlSeq(boardSeq);
	}

	/**
	 * Desc : 문의사항 답변 정보를 등록하는 메소드
	 * @Method Name : insertReCcQnaDetailPopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertReCcQnaDetailPopup(PSCPBRD0012VO vo) throws Exception {
		// 상담 문의 등록 관련 seq 얻어오기.
		DataMap map = selectBoardSeq();
		DataMap mapListSeq = selectBoardListlSeq(vo.getBoardSeq());
		vo.setUpBoardSeq(vo.getBoardSeq());
		vo.setBoardSeq(map.getString("BOARD_SEQ")); // 상담순번
		vo.setListSeq(mapListSeq.getString("LIST_SEQ")); // 목록순번
		
		pscpbrd0012Dao.insertReCcQnaDetailPopup(vo);
	}
	
	/**
	 * Desc : 문의사항 상세 정보를 수정하는 메소드
	 * @Method Name : updateCcQnaDetailPopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaDetailPopup(PSCPBRD0012VO vo) throws Exception {
		pscpbrd0012Dao.updateCcQnaDetailPopup(vo);
		//pscpbrd0012Dao.updateCcQnaDetailPopupReply(vo);
	}

	/**
	 * Desc : 문의사항 상세 조회시 조회수를 증가시키는 메소드
	 * @Method Name : updateCcQnaReadCount
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaReadCount(PSCPBRD0012VO vo) throws Exception {
		pscpbrd0012Dao.updateCcQnaReadCount(vo);
	}

	/**
	 * Desc : 다운로드 파일 정보를 삭제하는 메소드
	 * @Method Name : deleteAtchFileId
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteAtchFileId(PSCPBRD0012VO vo) throws Exception {
		pscpbrd0012Dao.deleteAtchFileId(vo);
	}
}
 