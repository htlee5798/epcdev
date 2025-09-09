/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 05. 31. 오후 2:30:50
 * @author      : kslee
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0013VO;

/**
 * @Class Name : PSCMBRD0013Service
 * @Description : 상품평 목록조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 05. 31. 오후 2:35:30 kslee
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMBRD0013Service {

	/**
	 * Desc : 상품평 게시판 리스트 가져오기
	 * @Method Name : selectNoticeList
	 * @param paramMap
	 * @throws Exception
	 * @return List<PSCMBRD0013VO>
	 */
	public List<PSCMBRD0013VO> selectProductSearch(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 상품평 게시판 총건수
	 * @Method Name : selectNoticeTotalCnt
	 * @param paramMap
	 * @throws Exception
	 * @return iTotCnt
	 */
	public int selectProductTotalCnt(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 상품평 게시판 상세정보
	 * @Method Name : selectProductView
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0003VO
	 */
	public PSCMBRD0013VO selectProductView(String recommSeq) throws Exception;

	/**
	 * Desc : 상품평 게시판 우수 상품평선정 및 해제 업데이트
	 * @Method Name : updateExlnSltYn
	 * @param PSCMBRD0013VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int updateExlnSltYn(List<PSCMBRD0013VO> pscmbrd0013VOList) throws Exception;

	/**
	 * Desc : 상품평 게시판 업데이트
	 * @Method Name : updateProduct
	 * @param PBOMBRD0003VO
	 * @throws SQLException
	 * @return 결과수
	 */
	public int updateProduct(PSCMBRD0013VO bean) throws Exception;

	/**
	 * Desc : 상품평 리스트 엑셀다운로드
	 * @Method Name : selectPscmbrd0013Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<Map<Object, Object>> selectPscmbrd0013Export(Map<Object, Object> paramMap) throws Exception;

	public DataMap selectProdPhotoView(DataMap paramMap) throws Exception;

	/**
	 * Desc : 체험형 상품 총 건수
	 * @Method Name : selectExprView
	 * @param reviewId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int selectExprTotalCnt(Map paramMap) throws Exception;

	/**
	 * Desc : 체험형 상품 목록조회
	 * @Method Name : selectExprSearch
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMBRD0013VO> selectExprSearch(Map paramMap) throws Exception;

	/**
	 * Desc : 체험형 상품 상세
	 * @Method Name : selectExprView
	 * @param reviewId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public PSCMBRD0013VO selectExprView(String recommSeq) throws Exception;

	/**
	 * Desc : 체험형 상품 상세
	 * @Method Name : selectExprView
	 * @param reviewId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updateExprProd(PSCMBRD0013VO bean) throws Exception;

	/**
	 * Desc : 체험형 엑셀 다운
	 * @Method Name : selectExprView
	 * @param reviewId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMBRD0013VO> selectPscmbrd001302Export(Map paramMap) throws Exception;

}
