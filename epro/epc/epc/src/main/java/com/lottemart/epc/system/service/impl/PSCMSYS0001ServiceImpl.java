package com.lottemart.epc.system.service.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.system.dao.PSCMSYS0001Dao;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.epc.system.service.PSCMSYS0001Service;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMSYS0001ServiceImpl.java
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
@Service("pscmsys0001Service")
public class PSCMSYS0001ServiceImpl implements PSCMSYS0001Service
{
	@Autowired
	private PSCMSYS0001Dao pscmsys0001Dao;
	
	/**
	 * (non-Javadoc)
 	 * @see com.lottemart.epc.system.service.PSCMSYS0001Service#selectDeliveryAmtInfoTotalCnt(java.util.Map)
	 * @MethodName  : PSCMSYS0001ServiceImpl.java
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @Description : 배송비정보 리스트의 갯수를 select 하여 리턴
     * @param 
	 * @return
	 */
	public int selectDeliveryAmtInfoTotalCnt(Map<String, String> paramMap) throws SQLException
	{
		return pscmsys0001Dao.selectDeliveryAmtInfoTotalCnt(paramMap);
	}
	
	/**
	 * (non-Javadoc)
 	 * @see com.lottemart.epc.system.service.PSCMSYS0001Service#selectDeliveryAmtInfoList(java.util.Map)
	 * @MethodName  : PSCMSYS0001ServiceImpl.java
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @Description : 배송비정보 리스트를 select 하여 리턴
     * @param 
	 * @return
	 */
	public List<PSCMSYS0001VO> selectDeliveryAmtInfoList(Map<String, String> paramMap) throws Exception
	{
		return pscmsys0001Dao.selectDeliveryAmtInfoList(paramMap);
	}
	
	/**
	 * (non-Javadoc)
 	 * @see com.lottemart.epc.system.service.PSCMSYS0001Service#selectDeliveryAmtList()
	 * @MethodName  : PSCMSYS0001ServiceImpl.java
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @Description : 배송비 등록 팝업 로드시 배송비 리스트(combo box에 들어갈)를 select 하여 리턴
     * @param 
	 * @return
	 */
	public List<PSCMSYS0001VO> selectDeliveryAmtList(Map<String, String> paramMap) throws SQLException
	{
		return pscmsys0001Dao.selectDeliveryAmtList(paramMap);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.system.service.PBOMADM0009Service#selecetLatestApplyStartDy(java.lang.String)
	 * @MethodName  : selecetLatestApplyStartDy.java
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @Description : 마지막 적용시작일자를 가져온다
				      적용일자 이력중 가증 큰 값. 새로운 적용일자를 등록하려면 최소한 이 값보다 커야하기 때문에 비교하기 위해서 가져온다
	 * @param vendorId
	 * @return
	 * @throws Exception
	 */
	public DataMap selectLatestApplyStartDy(Map<String, String> paramMap) throws Exception
	{
		return pscmsys0001Dao.selectLatestApplyStartDy(paramMap);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.system.service.PBOMADM0009Service#validateDeliveryAmtInsert
	 * @MethodName  : PBOMADM0009ServiceImpl.java
	 * @since      : 2011
	 * @version    :
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @Description : 배송비 정보를 insert 적용일자가 유효한지 확인한다
	 * @param 
	 * @return
	 */
	public DataMap validateDeliveryAmtInsert(Map<String, String> paramMap) throws Exception
	{
		return pscmsys0001Dao.validateDeliveryAmtInsert(paramMap);
	}
	
	/**
	 * 
	 * @see selecetLatestApplyStartDy
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @MethodName  : selecetLatestApplyStartDy
	 * @version    :
	 * @Description : 마지막 적용종료일자를 새로적용하려는 적용시작일자의 하루 전으로 update 한다
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateLatestApplyEndDy(Map<String, String> paramMap) throws Exception
	{
		pscmsys0001Dao.updateLatestApplyEndDy(paramMap);
	}
	
	/**
	 * (non-Javadoc)
	 	 * @see com.lottemart.epc.system.service.PBOMADM0009Service#insertDeliveryAmt
		 * @MethodName  : PBOMADM0009ServiceImpl.java
		 * @version    :
		 * @Locaton    : com.lottemart.epc.system.service.impl
		 * @Description : 저장버튼 클릭시 배송비 정보를 등록한다 
	     * @param 
		 * @return
	 */
	public void insertDeliveryAmt(Map<String, String> paramMap) throws Exception
	{
		pscmsys0001Dao.insertDeliveryAmt(paramMap);
	}
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.system.PBOMADM0009Service#selectTargetCnt
	 * @Locaton    : com.lottemart.epc.system.impl
	 * @MethodName  : selectTargetCnt
	 * @version    :
	 * @Description : 저장버튼 클릭 시 저장하려는 로우가 이미 존재하는지 확인하기위해 갯수를 리턴. 존재하면 update, 존재하지 않으면 insert 위해 (배송비수정팝업화면)
	 * @param pscmsys0001VO
	 * @return
	 * @throws Exception
	 */
	public int selectTargetCnt(Map<String, String> paramMap)  throws Exception{
		return pscmsys0001Dao.selectTargetCnt(paramMap);
		
	}
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.system.PBOMADM0009Service#updateDeliveryAmt
	 * @Locaton    : com.lottemart.epc.system.impl
	 * @MethodName  : updateDeliveryAmt
	 * @version    :
	 * @Description : 저장버튼 클릭 시 기준 하한/상한 금액을 update 한다
	 * @param pscmsys0001VO
	 * @throws Exception
	 */
	public void updateDeliveryAmt(Map<String, String> paramMap) throws Exception{
		pscmsys0001Dao.updateDeliveryAmt(paramMap);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.system.PBOMADM0009Service#updateDeliveryAmt20
	 * @Locaton    : com.lottemart.epc.system.impl
	 * @MethodName  : updateDeliveryAmt20
	 * @version    :
	 * @Description : 저장버튼 클릭 시 반품비 교환비 금액을 update 한다
	 * @param pscmsys0001VO
	 * @throws Exception
	 */
	public void updateDeliveryAmt20(Map<String, String> paramMap) throws Exception{
		pscmsys0001Dao.updateDeliveryAmt20(paramMap);
	}
	
	/**
	 * 
	 * @see deleteDeliveryAmt
	 * @Locaton    : com.lottemart.epc.system.impl
	 * @MethodName  : deleteDeliveryAmt
	 * @version    :
	 * @Description : 삭제 클릭시 해당하는 적용일자 배송비정보 모두 삭제한다
	 * @param pscmsys0001VO
	 * @throws Exception
	 */
	public int deleteDeliveryAmt(Map<String, String> paramMap) throws Exception{
		return pscmsys0001Dao.deleteDeliveryAmt(paramMap);
	}
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.system.service.PBOMADM0009Service#updateLatestApplyEndDy_AfterDelete
	 * @Locaton    : com.lottemart.epc.system.basicInfo.service.impl
	 * @MethodName  : updateLatestApplyEndDy_AfterDelete
	 * @version    :
	 * @Description : 적용예정일 삭제 후에 마지막 적용종료일자를 99991231 다시 설정한다 
	 * @param pscmsys0001VO
	 * @throws Exception
	 */
	public void updateLatestApplyEndDy_AfterDelete(Map<String, String> paramMap) throws Exception{
		pscmsys0001Dao.updateLatestApplyEndDy_AfterDelete(paramMap);
	}
	/**
	 * 
	 * @see deleteDeliveryAmt
	 * @Locaton    : com.lottemart.epc.system.impl
	 * @MethodName  : deleteDeliveryAmt
	 * @version    :
	 * @Description : 적용예정일 삭제 후에 마지막 적용종료일자를 99991231 다시 설정한다 
	 * @param pscmsys0001VO
	 * @throws Exception
	 */
	public int updateRecoveryDate(PSCMSYS0001VO pscmsys0001VO) throws Exception{
		return pscmsys0001Dao.updateRecoveryDate(pscmsys0001VO);
	}
	
}
