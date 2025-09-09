
package com.lottemart.epc.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0009Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0009VO;
import com.lottemart.epc.statistics.service.PSCMSTA0009Service;

/**
 * @Class Name : PSCMSTA0004ServiceImpl
 * @Description : 아시아나정산관리 목록 조회 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:31:52 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMSTA0009Service")
public class PSCMSTA0009ServiceImpl implements PSCMSTA0009Service {
	
	@Autowired
	private PSCMSTA0009Dao pscmsta0009Dao;

	public List<DataMap> selectLotteCardMallObjectCalList(PSCMSTA0009VO searchVO) throws Exception {
		return pscmsta0009Dao.getLotteCardMallObjectCalList(searchVO);
	}
	
	public List<DataMap> selectLotteCardMallObjectCalListExcel(PSCMSTA0009VO searchVO) throws Exception {
		return pscmsta0009Dao.getLotteCardMallObjectCalListExcel(searchVO);
	}
	
}
