
package com.lottemart.epc.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PDCPFND0001Dao;
import com.lottemart.epc.delivery.service.PDCPFND0001Service;


@Service("PDCPFND0001Service")
public class PDCPFND0001ServiceImpl implements PDCPFND0001Service {
	
	@Autowired
	private PDCPFND0001Dao pdcpfnd0001Dao;


	/** 
	 * @see selectAcceptList
	 * @Method Name  : PDCPFND0001ServiceImpl.java
	 * @since      : 2011. 12. 21.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 배송접수리스트
     * @param 
	 * @return  List<DataMap>
     * @throws 
     */
	public List<DataMap> selectAcceptList(DataMap paramMap) throws Exception{
		return pdcpfnd0001Dao.selectAcceptList(paramMap);
	}

	public DataMap selectStoreDetailInfo(String strCd) throws Exception{
		return pdcpfnd0001Dao.selectStoreDetailInfo(strCd);
	}
}
