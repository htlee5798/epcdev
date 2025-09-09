package com.lottemart.epc.system.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMSYS0001Dao.java
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
@Repository("pscmsys0001Dao")
public class PSCMSYS0001Dao extends AbstractDAO
{
	@Autowired
	private SqlMapClient sqlMapClient;
	/**
	 * 
	 * @see selectDeliveryAmtInfoTotalCnt
	 * @Method Name  : selectDeliveryAmtInfoTotalCnt
	 * @version    :
	 * @Locaton    : com.lottemart.system.dao
	 * @Description : 배송비정보 리스트의 갯수를 select 하여 리턴
     * @param 
	 * @return  int
     * @throws
	 */
	public int selectDeliveryAmtInfoTotalCnt(Map<String, String> paramMap) throws SQLException
	{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)sqlMapClient.queryForObject("pscmsys0001.selectDeliveryAmtInfoTotalCnt", paramMap);
	
		return iTotCnt.intValue();
	}
	
	/**
	 * 
	 * @see selectDeliveryAmtInfoList
	 * @Method Name  : selectDeliveryAmtInfoList
	 * @version    :
	 * @Locaton    : com.lottemart.system.dao
	 * @Description : 배송비정보 리스트를 select 하여 리턴
     * @param 
	 * @return  List<PSCMSYS0001VO>
     * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMSYS0001VO> selectDeliveryAmtInfoList(Map<String, String> paramMap) throws SQLException
	{
		return sqlMapClient.queryForList("pscmsys0001.selectDeliveryAmtInfoList", paramMap);
	}
	
	/**
	 * 
	 * @see selectDeliveryAmtList
	 * @Method Name  : selectDeliveryAmtList
	 * @version    :
	 * @Locaton    : com.lottemart.system.dao
	 * @Description : 배송비 등록 팝업 로드시 배송비 리스트(combo box에 들어갈)를 select 하여 리턴
     * @param 
	 * @return  DataMap
     * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMSYS0001VO> selectDeliveryAmtList(Map<String, String> paramMap) throws SQLException
	{
		return sqlMapClient.queryForList("pscmsys0001.selectDeliveryAmtList",paramMap);
	}
	
	/**
	 * 
	 * @see selecetLatestApplyStartDy
	 * @MethodName  : selecetLatestApplyStartDy
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @Description :  마지막 적용시작일자를 가져온다
	 * @param vendorId
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectLatestApplyStartDy(Map<String, String> paramMap) throws SQLException
	{
		return (DataMap)sqlMapClient.queryForObject("pscmsys0001.selectLatestApplyStartDy", paramMap);
	}
	
	/**
	 * 
	 * @see validateDeliveryAmtInsert
	 * @MethodName  : validateDeliveryAmtInsert
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @Description : 배송비 정보를 insert 적용일자가 유효한지 확인한다
	 * @param 
	 * @return  DataMap
	 * @throws
	 */
	public DataMap validateDeliveryAmtInsert(Map<String, String> paramMap) throws SQLException
	{
		return (DataMap)sqlMapClient.queryForObject("pscmsys0001.validateDeliveryAmtInsert",paramMap);
	}
	
	/**
	 * 
	 * @see updateLatestApplyEndDy
	 * @MethodName : updateLatestApplyEndDy
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @Description : 마지막 적용종료일자를 새로적용하려는 적용시작일자의 하루 전으로 update 한다
	 * @param VO
	 * @return void
	 * @throws SQLException
	 */
	public void updateLatestApplyEndDy(Map<String, String> paramMap) throws SQLException
	{
		sqlMapClient.update("pscmsys0001.updateLatestApplyEndDy",paramMap);
	}
	
	/**
	 * 
	 * @see insertDeliveryAmt
	 * @Method Name  : insertDeliveryAmt
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @Description : 배송비 정보를 등록한다
	 * @param VO
	 * @return void
	 * @throws SQLException
	 */
	public void insertDeliveryAmt(Map<String, String> paramMap) throws SQLException
	{
		sqlMapClient.insert("pscmsys0001.insertDeliveryAmt", paramMap);
	}
	
	/**
	 * 
	 * @see selectTargetCnt
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @MethodName  : selectTargetCnt
	 * @Description : 저장버튼 클릭 시 저장하려는 로우가 이미 존재하는지 확인하기위해 갯수를 리턴. 존재하면 update, 존재하지 않으면 insert 위해 (배송비수정팝업화면)
	 * @param pscmsys0001VO
	 * @return
	 * @throws SQLException
	 */
	public int selectTargetCnt(Map<String, String> paramMap)  throws SQLException{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)sqlMapClient.queryForObject("pscmsys0001.selectTargetCnt",paramMap);
		return iTotCnt.intValue();
	}
	
	/**
	 * 
	 * @see updateDeliveryAmt
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @MethodName  : updateDeliveryAmt
	 * @Description : 저장버튼 클릭 시 기준 하한/상한 금액을 update 한다
	 * @param pscmsys0001VO
	 * @throws SQLException
	 */
	public void updateDeliveryAmt(Map<String, String> paramMap) throws SQLException{
		sqlMapClient.update("pscmsys0001.updateDeliveryAmt",paramMap);
	}
	
	/**
	 * 
	 * @see updateDeliveryAmt20
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @MethodName  : updateDeliveryAmt20
	 * @Description : 저장버튼 클릭 시 기준 반품비 교환비 금액을 update 한다
	 * @param pscmsys0001VO
	 * @throws SQLException
	 */
	public void updateDeliveryAmt20(Map<String, String> paramMap) throws SQLException{
		sqlMapClient.update("pscmsys0001.updateDeliveryAmt20",paramMap);
	}
	
	/**
	 * 
	 * @see deleteDeliveryAmt
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @MethodName  : deleteDeliveryAmt
	 * @Description : 삭제 클릭시 해당하는 적용일자 배송비정보 모두 삭제한다
	 * @param pscmsys0001VO
	 * @throws SQLException
	 */
	public int deleteDeliveryAmt(Map<String, String> paramMap) throws SQLException{
		return sqlMapClient.delete("pscmsys0001.deleteDeliveryAmt", paramMap);
	}
	
	/**
	 * 
	 * @see updateLatestApplyEndDy_AfterDelete
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @MethodName  : updateLatestApplyEndDy_AfterDelete
	 * @Description :  적용예정일 삭제 후에 마지막 적용종료일자를 99991231 다시 설정한다
	 * @param pscmsys0001VO
	 * @throws SQLException
	 */
	public void updateLatestApplyEndDy_AfterDelete(Map<String, String> paramMap) throws SQLException{
		sqlMapClient.update("pscmsys0001.updateLatestApplyEndDy_AfterDelete", paramMap);
	}
	
	/**
	 * 
	 * @see deleteDeliveryAmt
	 * @Locaton    : com.lottemart.epc.system.dao
	 * @MethodName  : deleteDeliveryAmt
	 * @Description : 적용예정일 삭제 후에 마지막 적용종료일자를 99991231 다시 설정한다
	 * @param pscmsys0001VO
	 * @throws SQLException
	 */
	public int updateRecoveryDate(PSCMSYS0001VO pscmsys0001VO) throws SQLException{
		return sqlMapClient.update("pscmsys0001.updateRecoveryDate", pscmsys0001VO);
	}

}
