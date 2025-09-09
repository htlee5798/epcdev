/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.calculation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.calculation.dao.PSCMCAL0003Dao;
import com.lottemart.epc.calculation.model.PSCMCAL0003SearchVO;
import com.lottemart.epc.calculation.service.PSCMCAL0003Service;

/**
 * @Class Name : PSCMCAL0003ServiceImpl
 * @Description : 배송료 정산 목록 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:11:26 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMCAL0003Service")
public class PSCMCAL0003ServiceImpl implements PSCMCAL0003Service {
	
	@Autowired
	private PSCMCAL0003Dao pscmcal0003Dao;
	
	public List<PSCMCAL0003SearchVO> selectDeliverySettleCostsCalculateList(DataMap paramMap) throws Exception {
		return pscmcal0003Dao.getDeliverySettleCostsCalculateList(paramMap);
	}
	
	public DataMap selectDeliverySettleCostsCalculateSum(DataMap paramMap) throws Exception {
		return pscmcal0003Dao.getDeliverySettleCostsCalculateSum(paramMap);
	}
}
