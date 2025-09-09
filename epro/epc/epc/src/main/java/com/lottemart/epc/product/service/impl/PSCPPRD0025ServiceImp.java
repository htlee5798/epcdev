package com.lottemart.epc.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0025Dao;
import com.lottemart.epc.product.model.PSCPPRD0025HistVO;
import com.lottemart.epc.product.model.PSCPPRD0025VO;
import com.lottemart.epc.product.service.PSCPPRD0025Service;

/**
 * @Class Name : PSCPPRD0025ServiceImp.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 11. 21. 오후 1:29:29 최성웅
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */

@Service("PSCPPRD0025service")
public class PSCPPRD0025ServiceImp implements PSCPPRD0025Service{
	@Autowired
	private PSCPPRD0025Dao pscpprd0025Dao;

	/**
	 * Desc : 키워드 조회
	 * @Method Name : selectKeywordSearch
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectKeywordSearch(DataMap dataMap) throws Exception {
		return pscpprd0025Dao.selectKeywordSearch(dataMap);
	}

	/**
	 * Desc : 키워드 수정
	 * @Method Name : keywordUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@Override
	public void keywordUpdate(PSCPPRD0025VO pscpprd0025Vo) throws Exception {
		pscpprd0025Dao.keywordUpdate(pscpprd0025Vo);
	}
	
	/**
	 * Desc : 키워드 마스터 수정
	 * @Method Name : masterKeywordUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@Override
	public void masterKeywordUpdate(PSCPPRD0025VO pscpprd0025Vo) throws Exception {
		pscpprd0025Dao.masterKeywordUpdate(pscpprd0025Vo);
	}
	
	/**
	 * Desc : 키워드 삭제
	 * @Method Name : keywordDelete
	 * @param pscpprd00025Vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@Override
	public void keywordDelete(PSCPPRD0025VO pscpprd0025Vo) throws Exception {
		pscpprd0025Dao.keywordDelete(pscpprd0025Vo);
	}
	
	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 이력 수정
	 * @Method keywordHistUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 */
	@Override
	public void keywordHistUpdate(PSCPPRD0025HistVO pscpprd0025HistVo) throws Exception {
		pscpprd0025Dao.keywordHistUpdate(pscpprd0025HistVo);
	}
	
	/**
	 * 상품키워드 마스터 이력 수정
	 * @Method masterKeywordHistUpdate
	 * @param pscpprd00025HistVo
	 * @throws Exception
	 */
	@Override
	public void masterKeywordHistUpdate(PSCPPRD0025HistVO pscpprd0025HistVo) throws Exception {
		pscpprd0025Dao.masterKeywordHistUpdate(pscpprd0025HistVo);
	}
	//20180911 상품키워드 입력 기능 추가
	
}
