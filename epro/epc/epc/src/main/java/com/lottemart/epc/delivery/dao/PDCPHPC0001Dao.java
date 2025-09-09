package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PDCPHPC0001VO;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.dao
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 6.	binary27	
 * @version : 
 */
@Repository("PDCPHPC0001Dao")
public class PDCPHPC0001Dao {

	@Autowired
	private SqlMapClient sqlMapClient;


	/** 
	 * @see selectHpCallRegiInfo
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 등록에 필요한 정보를 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public DataMap selectHpCallRegiInfo(String param) throws SQLException{
		return (DataMap)sqlMapClient.queryForObject("PDCPHPC0001.selectHpCallRegiInfo", param);
	}
	
	
	/** 
	 * @see selectProdInfo
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2012. 1. 10.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 배송 상품 정보 조회
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdInfo(String param) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCPHPC0001.selectProdInfo",param);
	}
	
	
	/** 
	 * @see selectHpcInfo
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 모든 해피콜 정보를 조회한다.
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectHpcInfo(String param) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCPHPC0001.selectHpcInfo", param);
	}
	
	
	/** 
	 * @see getAdminId
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 8.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : adminId를 받아온다.
     * @param 
	 * @return  String
     * @throws 
	*/
	public String getAdminId(String param) throws SQLException{
		return (String)sqlMapClient.queryForObject("PDCPHPC0001.getAdminId", param);
	}
	
	
	/** 
	 * @see insertHappycall
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 새로운 해피콜정보 저장
     * @param 
	 * @return  void
     * @throws 
	*/
	public void insertHappycall(PDCPHPC0001VO pdcphpc0001VO) throws SQLException{
		sqlMapClient.insert("PDCPHPC0001.insertHappycall", pdcphpc0001VO);
	}
	

	/** 
	 * @see updateInvoiceAcceptHappycall
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 등록에 따른 송장접수정보수정 
     * @param 
	 * @return  int
     * @throws 
	*/
	public int updateInvoiceAcceptHappycall(PDCPHPC0001VO pdcphpc0001VO) throws SQLException{
		return sqlMapClient.update("PDCPHPC0001.updateInvoiceAcceptHappycall", pdcphpc0001VO);
	}
		
	public int updateSendInfo(PDCPHPC0001VO pdcphpc0001VO) throws SQLException{
		return sqlMapClient.update("PDCPHPC0001.updateSendInfo", pdcphpc0001VO);
	}
	
	/** 
	 * @see updateRecvInfo
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    : 해피콜 등록에 따른 받으실분 정보 수정
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 
     * @param 
	 * @return  int
     * @throws 
	*/
	public int updateRecvInfo(PDCPHPC0001VO pdcphpc0001VO) throws SQLException{
		return sqlMapClient.update("PDCPHPC0001.updateRecvInfo", pdcphpc0001VO);
	}
	
	
	/** 
	 * @see selectPostList
	 * @Method Name  : PDCPHPC0001Dao.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 우편번호를 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPostList(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("PDCPHPC0001.selectPostList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPostList_JIBUN(DataMap param) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("PDCPHPC0001.selectPostList_JIBUN", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPostList_ROAD(DataMap param) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("PDCPHPC0001.selectPostList_ROAD", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNewZipCodeSigunguList(DataMap param) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("PDCPHPC0001.selectNewZipCodeSigunguList", param);
	}
	
	
}
