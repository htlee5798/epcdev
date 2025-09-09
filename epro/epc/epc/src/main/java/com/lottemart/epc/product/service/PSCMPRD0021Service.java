package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * 
 * @author khKim
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.service
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  khKim
 * @version : 
 * </pre>
 */
public interface PSCMPRD0021Service {

	/**
	 * 
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.service
	 * @MethodName  : selectRepProdCdList
	 * @author     : khKim
	 * @Description : 대표상품코드 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<DataMap> selectRepProdCdList(DataMap paramMap) throws Exception;

}
