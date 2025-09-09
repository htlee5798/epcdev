package com.lottemart.epc.system.service;


import java.util.List;
import java.util.Map;

import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMSYS0001Service.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCMSYS0001Service 
{
	public int selectDeliveryAmtInfoTotalCnt(Map<String, String> paramMap) throws Exception;
	
	public List<PSCMSYS0001VO> selectDeliveryAmtInfoList(Map<String, String> paramMap) throws Exception;
	
	public List<PSCMSYS0001VO> selectDeliveryAmtList(Map<String, String> paramMap) throws Exception;
	
	public DataMap selectLatestApplyStartDy(Map<String, String> paramMap) throws Exception;
	
	public DataMap validateDeliveryAmtInsert(Map<String, String> paramMap) throws Exception;
	
	public void updateLatestApplyEndDy(Map<String, String> paramMap) throws Exception;

	public void insertDeliveryAmt(Map<String, String> paramMap) throws Exception;
	
	public int selectTargetCnt(Map<String, String> paramMap)  throws Exception;
	
	public void updateDeliveryAmt(Map<String, String> paramMap) throws Exception;
	
	public void updateDeliveryAmt20(Map<String, String> paramMap) throws Exception;
	
	public int deleteDeliveryAmt(Map<String, String> paramMap) throws Exception;
	
	public void updateLatestApplyEndDy_AfterDelete(Map<String, String> paramMap) throws Exception;
	
	public int updateRecoveryDate(PSCMSYS0001VO pscmsys0001VO) throws Exception;

}
