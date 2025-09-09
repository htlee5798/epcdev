
package com.lottemart.epc.calculation.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.calculation.model.PSCMCAL0003SearchVO;

/**
 * @Class Name : PSCMCAL0002Service
 * @Description : 택배비 정산 목록 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:11:28 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMCAL0003Service {

	/**
	 * Desc : 배송료정산 목록 조회 메소드
	 * @Method Name : selectDeliverySettleCostsCalculateList
	 * @param datamap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMCAL0003SearchVO> selectDeliverySettleCostsCalculateList(DataMap paramMap) throws Exception;
	
	/**
	 * Desc : 배송료정산 목록 조회 메소드
	 * @Method Name : selectDeliverySettleCostsCalculateSum
	 * @param datamap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap selectDeliverySettleCostsCalculateSum(DataMap paramMap) throws Exception;	
}
