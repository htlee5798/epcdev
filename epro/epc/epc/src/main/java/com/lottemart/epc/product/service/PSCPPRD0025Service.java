package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0025HistVO;
import com.lottemart.epc.product.model.PSCPPRD0025VO;

/**
 * @Class Name : PSCPPRD0025Service.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 11. 21. 오후 1:29:33 최성웅
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCPPRD0025Service {

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
	public List<DataMap> selectKeywordSearch(DataMap dataMap) throws Exception;
	

	/**
	 * Desc :  키워드 수정
	 * @Method Name : keywordUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void keywordUpdate(PSCPPRD0025VO pscpprd0025Vo) throws Exception;
	
	/**
	 * Desc : 키워드 마스터 수정
	 * @Method Name : masterKeywordUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void masterKeywordUpdate(PSCPPRD0025VO pscpprd0025Vo) throws Exception;
	
	/**
	 * Desc : 키워드 삭제
	 * @Method Name : keywordDelete
	 * @param pscpprd00025Vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void keywordDelete(PSCPPRD0025VO pscpprd0025Vo) throws Exception;
	
	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 이력 수정
	 * @Method keywordHistUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 */
	public void keywordHistUpdate(PSCPPRD0025HistVO pscpprd0025HistVo) throws Exception;
	
	/**
	 * 상품키워드 마스터 이력 수정
	 * @Method masterKeywordHistUpdate
	 * @param pscpprd00025HistVo
	 * @throws Exception
	 */
	public void masterKeywordHistUpdate(PSCPPRD0025HistVO pscpprd0025HistVo) throws Exception;
	//20180911 상품키워드 입력 기능 추가
	
}
