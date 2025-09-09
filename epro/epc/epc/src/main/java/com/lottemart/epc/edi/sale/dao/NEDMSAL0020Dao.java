package com.lottemart.epc.edi.sale.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.sale.model.NEDMSAL0020VO;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;


@Repository("nedmsa0020Dao")
public class NEDMSAL0020Dao extends AbstractDAO {
	
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : NEDMSAL0020Dao.java
	 * @since      : 2015. 11. 13
	 * @author     : 
	 * @version    :
	 * @Locaton    : com.lottemart.epc.edi.sale.dao
	 * @Description : 매출정보-점포별
     * @param 
	 * @return  List<DataMap>
     * @throws 
    */
	/*매출정보-점포별 */
	public List<NEDMSAL0020VO> selectStoreInfo(NEDMSAL0020VO map) throws Exception{
		return (List<NEDMSAL0020VO>)getSqlMapClientTemplate().queryForList("NEDMSAL0020.TSC_STORE-SELECT01",map);
	}
	
}
