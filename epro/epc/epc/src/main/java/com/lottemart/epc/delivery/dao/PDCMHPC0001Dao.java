package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.dao
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 5.	binary27	
 * @version : 
 */
@Repository("PDCMHPC0001Dao")
public class PDCMHPC0001Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/** 
	 * @see selectHappyCallList
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2011. 12. 5.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 정보 목록을 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectHappyCallList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCMHPC0001.selectHappyCallList", paramMap);
	}
	
	/** 
	 * @see selectHappyCallCount
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2019. 4. 11.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 실적을 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectHappyCallCount(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCMHPC0001.selectHappyCallCountNew", paramMap);
	}
	

	/** 
	 * @see selectConfInfo
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 조회일자차수 정보조회
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public DataMap selectConfInfo(String year) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("PDCMHPC0001.selectConfInfo", year);
	}
	

	/** 
	 * @see selectStoreList
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 점포 전체 목록 조회
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectStoreList() throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("PDCMHPC0001.selectStoreList");
	}
	
	/** 
	 * @see selectFromToDate
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public DataMap selectFromToDate(DataMap paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("PDCMHPC0001.selectFromToDate", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectActiveIdList() throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("PDCMHPC0001.selectActiveIdList");
	}

	public void updatdUtakmnId(DataMap paramMap) throws Exception{
		sqlMapClient.update("PDCMHPC0001.updatdUtakmnId", paramMap);
		
	}
	
	/** 
	 * @see selectHappyCallCountTotal
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectHappyCallCountTotal(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCMHPC0001.selectHappyCallCountTotal", paramMap);
	}
	
	/** 
	 * @see selectHappyCallCountDeliDy
	 * @Method Name  : PDCMHPC0001Dao.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.dao
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List<DataMap> selectHappyCallCountDeliDy(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCMHPC0001.selectHappyCallCountDeliDy", paramMap);
	}
}
