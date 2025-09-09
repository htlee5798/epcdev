/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0009VO;

/**
 * @Class Name : PSCMPRD0009Service
 * @Description : 상품가격변경요청리스트를 조회하는 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:57:32 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMPRD0009Service {

	/**
	 * Desc : 상품가격변경요청리스트 조회하는 메소드
	 * @Method Name : selectPriceChangeList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0009VO> selectPriceChangeList(DataMap paramMap) throws Exception;
	
	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드
	 * @Method Name : insertPriceChangeReq
	 * @param pscmprd0009VO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int insertPriceChangeReq(PSCMPRD0009VO pscmprd0009VO) throws Exception;
	
	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드
	 * @Method Name : insertPriceChangeReq
	 * @param pscmprd0009VO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updatePriceChangeReq(PSCMPRD0009VO pscmprd0009VO) throws Exception;
	
	/**
	 * Desc : 상품단품 정보를 조회하는 메소드
	 * @Method Name : selectProductItemList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0009VO> selectProductItemList(DataMap paramMap) throws Exception;

	/**
	 * Desc : 상품단품 정보 중복 카운트를 조회하는 메소드
	 * @Method Name : selectProductItemDupCount
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectProductItemDupCount(DataMap paramMap) throws Exception;

	/**
	 * Desc : 상품가격변경요청리스트 엑셀다운로드하는 메소드
	 * @Method Name : selectPriceChangeListExcel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0009VO> selectPriceChangeListExcel(PSCMPRD0009VO vo) throws Exception;
	
}
