package com.lottemart.epc.product.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0005Dao;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.service.PSCPPRD0005Service;

/**
 * @Class Name : PSCPPRD0005ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscpprd0005Service")
public class PSCPPRD0005ServiceImpl implements PSCPPRD0005Service {

	@Autowired
	private PSCPPRD0005Dao pscpprd0005Dao;

	/**
	 * 추가설명 정보
	 * @param Map<String, String>
	 * @return PSCPPRD0005VO
	 * @throws Exception
	 */
	public PSCPPRD0005VO selectPrdDescription(Map<String, String> paramMap) throws Exception {
		return pscpprd0005Dao.selectPrdDescription(paramMap);
	}

	/**
	 * 추가설명 정보 입력 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdDescription(PSCPPRD0005VO bean) throws Exception {
		int resultCnt = 0;

		try {
			// 추가설명 입력
			resultCnt = pscpprd0005Dao.insertPrdDescription(bean);

			if (resultCnt <= 0) {
				throw new TopLevelException("추가설명 입력 작업중에 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

	/**
	 * 추가설명 정보 수정 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdDescription(PSCPPRD0005VO bean) throws Exception {
		int resultCnt = 0;

		try {
			// 추가설명 수정
			resultCnt = pscpprd0005Dao.updatePrdDescription(bean);
			if (resultCnt <= 0) {
				throw new TopLevelException("추가설명 수정 작업중에 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

	/**
	 * 추가설명 정보 로그
	 * @param bean
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdDescrLog(PSCPPRD0005VO bean) throws Exception {
		int resultCnt = 0;
		
		try {
			// 추가설명 입력
			resultCnt = pscpprd0005Dao.insertPrdDescrLog(bean);
			
		} catch (Exception e) {
			throw e;
		}
		
		return resultCnt;
	}

	/**
	 * 추가설명 정보
	 * @param Map<String, String>
	 * @return PSCPPRD0005VO
	 * @throws Exception
	 */
	public PSCPPRD0005VO selectPrdMdAprvMst(Map<String, String> paramMap) throws Exception {
		return pscpprd0005Dao.selectPrdMdAprvMst(paramMap);
	}

	/**
	 * 상품 MD승인 히스토리 마스터 카운트
	 * @param DataMap
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdMdAprvMstCnt(DataMap paramMap) throws Exception {
		return pscpprd0005Dao.selectPrdMdAprvMstCnt(paramMap);
	}

	/**
	 * 추가설명 히스토리 정보 수정 처리
	 * @param VO	
	 * @return int
	 * @throws Exception
	 */
	public String insertPrdMdAprvMst(PSCPPRD0005VO bean) throws Exception {
		return (String) pscpprd0005Dao.insertPrdMdAprvMst(bean);
	}

	/**
	 * 추가설명 히스토리 마스터 정보 입력 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdDescriptionHist(PSCPPRD0005VO bean) throws Exception {
		int resultCnt = 0;

		try {
			String seq = pscpprd0005Dao.insertPrdMdAprvMst(bean);
			bean.setSeq(seq);

			// 추가설명 입력
			resultCnt = pscpprd0005Dao.insertPrdDescriptionHist(bean);

			if (resultCnt <= 0) {
				throw new TopLevelException("추가설명 히스토리 마스터 입력 작업중에 오류가 발생하였습니다.");
			}
			pscpprd0005Dao.insertPrdDescrLog(bean);
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

	/**
	 * 추가설명 히스토리 정보 수정 처리
	 * @param VO	
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdDescriptionHist(PSCPPRD0005VO bean) throws Exception {
		int resultCnt = 0;

		try {
			// 추가설명 수정
			resultCnt = pscpprd0005Dao.updatePrdDescriptionHist(bean);
			if (resultCnt <= 0) {
				throw new TopLevelException("추가설명 히스토리 수정 작업중에 오류가 발생하였습니다.");
			}
			pscpprd0005Dao.insertPrdDescrLog(bean);
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

}
