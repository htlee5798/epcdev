package com.lottemart.epc.delivery.service;

import java.sql.SQLException;
import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.service
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 5.	binary27
 * @version : 
 */
public interface PDCMHPC0001Service {

	/** 
	 * @see selectHappyCallList
	 * @Method Name  : PDCMHPC0001Service.java
	 * @since      : 2011. 12. 5.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 해피콜 정보 목록을 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectHappyCallList(DataMap paramMap) throws Exception;
	
	
	/** 
	 * @see selectHappyCallCount
	 * @Method Name  : PDCMHPC0001Service.java
	 * @since      : 2019. 4. 11.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 해피콜 실적을 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectHappyCallCount(DataMap paramMap) throws Exception;
	
	
	/**
	 * @param   
	 * @see selectConfInfo
	 * @Method Name  : PDCMHPC0001Service.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 조회일자차수 정보조회
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public DataMap selectConfInfo() throws Exception;
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : PDCMHPC0001Service.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 점포 전체 목록 조회
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectStoreList() throws SQLException;


		/**
		 * @Description : 해피콜 아이디 정보 조회
		 * @Author : EAJO
		 * @Method Name : selectActiveIds
		 * @param : 
		 * @return : Object
		 * @throws Exception
		 * @exception Exception
		 */
	public List<DataMap> selectActiveIdList() throws Exception;


		/**
		 * @Description : 
		 * @Author : EAJO
		 * @Method Name : updatdUtakmnId
		 * @param : 
		 * @return : void
		 * @throws Exception
		 * @exception Exception
		 */
	public void updatdUtakmnId(DataMap paramMap) throws Exception;
	
	/** 
	 * @see selectHappyCallCountTotal
	 * @Method Name  : PDCMHPC0001Service.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectHappyCallCountTotal(DataMap paramMap) throws Exception;
	
	/** 
	 * @see selectHappyCallCountDeliDy
	 * @Method Name  : PDCMHPC0001Service.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectHappyCallCountDeliDy(DataMap paramMap) throws Exception;

}
