/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.calculation.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.calculation.model.PSCMCAL0002VO;

/**
 * @Class Name : PSCMCAL0002Service
 * @Description : 배송료 정산 목록 Service 클래스
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
public interface PSCMCAL0002Service {

	/**
	 * Desc : 배송료 정산 목록수를 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateCount
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectDeliveryCostsCalculateCount(PSCMCAL0002VO searchVO) throws Exception;
	
	/**
	 * Desc : 배송료 정산 목록을 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectDeliveryCostsCalculateList(PSCMCAL0002VO searchVO) throws Exception;

	/**
	 * Desc : 배송료 정산 주문 통계를 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateOrderStats
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectDeliveryCostsCalculateOrderStats(PSCMCAL0002VO searchVO) throws Exception;

	/**
	 * Desc : 배송료 정산 배송료 통계를 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateDeliveryStats
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectDeliveryCostsCalculateDeliveryStats(PSCMCAL0002VO searchVO) throws Exception;

	/**
	 * Desc : 배송료 정산 목록을 엑셀다운로드하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateListExcel
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectDeliveryCostsCalculateListExcel(PSCMCAL0002VO searchVO) throws Exception;

}
