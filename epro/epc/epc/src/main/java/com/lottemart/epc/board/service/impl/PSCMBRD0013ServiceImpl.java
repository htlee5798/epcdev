/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 05. 31. 오후 2:36:30
 * @author      : kslee
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0013VO;
import com.lottemart.epc.common.util.EPCUtil;
import com.lottemart.epc.board.dao.PSCMBRD0013Dao;
import com.lottemart.epc.board.service.PSCMBRD0013Service;

/**
 * @Class Name : PSCMBRD0013ServiceImpl
 * @Description : 상품평 목록조회 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 05. 31. 오후 2:37:05 kslee
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMBRD0013Service")
public class PSCMBRD0013ServiceImpl implements PSCMBRD0013Service {

	@Autowired
	private PSCMBRD0013Dao pscmbrd0013Dao;


	/**
	 * Desc : 상품평 게시판 리스트 가져오기
	 * @Method Name : selectProductSearch
	 * @param paramMap
	 * @throws Exception
	 * @return List<PBOMBRD0002VO>
	 */
	@Override
	public List<PSCMBRD0013VO> selectProductSearch(Map<String, String> paramMap) throws Exception {
		return pscmbrd0013Dao.selectProductSearch(paramMap);
	}

	/**
	 * Desc : 상품평 게시판 총건수
	 * @Method Name : selectProductTotalCnt
	 * @param paramMap
	 * @throws Exception
	 * @return iTotCnt
	 */
	@Override
	public int selectProductTotalCnt(Map<String, String> paramMap) throws Exception {
		return pscmbrd0013Dao.selectProductTotalCnt(paramMap);
	}

	/**
	 * Desc : 상품평 게시판 상세정보
	 * @Method Name : selectProductView
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0003VO
	 */
	@Override
	public PSCMBRD0013VO selectProductView(String recommSeq) throws Exception {
		EPCUtil util = new EPCUtil();

		PSCMBRD0013VO vo = pscmbrd0013Dao.selectProductView(recommSeq);
		vo.setTitle(util.convertHtmlchars(vo.getTitle()));
		return vo;
	}

	/**
	 * Desc : 상품평 게시판 우수 상품평선정 및 해제 업데이트
	 * @Method Name : updateExlnSltYn
	 * @param PSCMBRD0013VO
	 * @throws Exception
	 * @return 결과수
	 */
	@Override
	public int updateExlnSltYn(List<PSCMBRD0013VO> pscmbrd0013VOList) throws Exception {
		int resultCnt = 0;
		int resultCnt1 = 0;
		try {
			int size = pscmbrd0013VOList.size();
			for (int i = 0; i < size; i++) {
				PSCMBRD0013VO ListBean = pscmbrd0013VOList.get(i);
				PSCMBRD0013VO bean = new PSCMBRD0013VO();
				bean.setExlnSltYn(ListBean.getExlnSltYn());
				bean.setModId(ListBean.getModId());
				bean.setRecommSeq(ListBean.getRecommSeq());
				resultCnt = pscmbrd0013Dao.updateExlnSltYn(bean);
				resultCnt1 = resultCnt1 + 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return resultCnt1;
	}

	/**
	 * Desc : 상품평 게시판 업데이트
	 * @Method Name : updateProduct
	 * @param PBOMBRD0003VO
	 * @throws SQLException
	 * @return 결과수
	 */
	@Override
	public int updateProduct(PSCMBRD0013VO bean) throws Exception {
		int resultCnt = 0;
		String adminSeq = "";
		try {
			resultCnt = pscmbrd0013Dao.updateProduct(bean);
		} catch (Exception e) {
			return 0;
		}
		return resultCnt;
	}

	/* (non-Javadoc)
	 * @see com.lottemart.bos.board.service.PBOMBRD0003Service#selectPscmbrd0013Export(java.util.Map)
	 */
	@Override
	public List<Map<Object, Object>> selectPscmbrd0013Export(
			Map<Object, Object> paramMap) throws Exception {
		return pscmbrd0013Dao.selectPscmbrd0013Export(paramMap);
	}

	@Override
	public DataMap selectProdPhotoView(DataMap paramMap) throws Exception {

		return pscmbrd0013Dao.selectProdPhotoView(paramMap);
	}

	@Override
	public int selectExprTotalCnt(Map paramMap) throws Exception {
		return pscmbrd0013Dao.selectExprTotalCnt(paramMap);
	}

	@Override
	public List<PSCMBRD0013VO> selectExprSearch(Map paramMap) throws Exception {
		return pscmbrd0013Dao.selectExprSearch(paramMap);
	}

	@Override
	public PSCMBRD0013VO selectExprView(String recommSeq) throws Exception {
		return pscmbrd0013Dao.selectExprView(recommSeq);
	}

	@Override
	public int updateExprProd(PSCMBRD0013VO bean) throws Exception {
		return pscmbrd0013Dao.updateExprProd(bean);
	}

	@Override
	public List<PSCMBRD0013VO> selectPscmbrd001302Export(Map paramMap) throws Exception {
		return pscmbrd0013Dao.selectPscmbrd001302Export(paramMap);
	}

}
