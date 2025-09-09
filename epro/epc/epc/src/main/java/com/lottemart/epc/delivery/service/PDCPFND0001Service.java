package com.lottemart.epc.delivery.service;

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
 * 2011. 12. 6.	binary27
 * @version : 
 */
public interface PDCPFND0001Service {

	/** 
	 * @see selectAcceptList
	 * @Method Name  : PDCPFND0001Service.java
	 * @since      : 2011. 12. 21.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service
	 * @Description : 배송접수리스트
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectAcceptList(DataMap paramMap) throws Exception;
	
	public DataMap selectStoreDetailInfo(String strCd) throws Exception;
}
