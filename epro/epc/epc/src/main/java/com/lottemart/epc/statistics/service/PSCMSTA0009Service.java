
/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0009VO;

/**
 * @Class Name : PSCMSTA0009Service
 * @Description : 아시아나정산관리 목록 조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:31:54 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMSTA0009Service {
	

	public List<DataMap> selectLotteCardMallObjectCalList(PSCMSTA0009VO searchVO) throws Exception;
		
	public List<DataMap> selectLotteCardMallObjectCalListExcel(PSCMSTA0009VO searchVO) throws Exception;

}