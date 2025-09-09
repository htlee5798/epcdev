/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0008Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0008SearchVO;
import com.lottemart.epc.statistics.service.PSCMSTA0008Service;

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
@Service("PSCMSTA0008Service")
public class PSCMSTA0008ServiceImpl implements PSCMSTA0008Service {
	
	@Autowired
	private PSCMSTA0008Dao pscmsta0008Dao;

	/**
	 * Desc : 아시아나정산관리 목록 조회 메소드
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectLotteCardMallCalList(PSCMSTA0008SearchVO searchVO) throws Exception {
		return pscmsta0008Dao.getLotteCardMallCalList(searchVO);
	}
	
	/**
	 * Desc : 아시아나정산관리 목록 엑셀조회 메소드
	 * @Method Name : selectAsianaMileageListExcel
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectLotteCardMallCalListExcel(PSCMSTA0008SearchVO searchVO) throws Exception {
		return pscmsta0008Dao.getLotteCardMallCalListExcel(searchVO);
	}
}
