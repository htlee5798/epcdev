package com.lottemart.epc.delivery.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PDCPHPC0001VO;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.service
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 6.	binary27
 * @version : 
 */
public interface PDCPHPC0001Service {

	/** 
	 * @see selectHpCallRegiInfo
	 * @Method Name  : PDCPHPC0001Service.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 해피콜 등록에 필요한 정보를 조회한다.
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public DataMap selectHpCallRegiInfo(String param) throws Exception;
	
	
	/** 
	 * @see selectProdInfo
	 * @Method Name  : PDCPHPC0001Service.java
	 * @since      : 2012. 1. 10.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 해피콜 배송 상품 정보 조회 
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectProdInfo(String param) throws SQLException;
	/** 
	 * @see selectHpcInfo
	 * @Method Name  : PDCPHPC0001Service.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 모든 해피콜 정보를 조회한다.
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public List<DataMap> selectHpcInfo(String param) throws Exception;
	
	
	/** 
	 * @see updateHappyCall
	 * @Method Name  : PDCPHPC0001Service.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 새로운 해피콜 정보를 저장한다.
     * @param 
	 * @return  int
     * @throws 
	*/
	public int updateHappyCall(PDCPHPC0001VO pdcphpc0001VO) throws Exception;
	
	
	/** 
	 * @see getAdminId
	 * @Method Name  : PDCPHPC0001Service.java
	 * @since      : 2011. 12. 8.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : adminId를 받아온다.
     * @param 
	 * @return  String
     * @throws 
	*/
	public String getAdminId(String param) throws Exception;
	
	
	/** 
	 * @see selectPostList
	 * @Method Name  : PDCPHPC0001Service.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 우편번호를 조회한다.
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectPostList(String addressDivn, DataMap paramMap) throws SQLException;
	
	public List<DataMap> selectNewZipCodeSigunguList(DataMap param) throws Exception;
}
