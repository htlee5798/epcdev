package com.lottemart.epc.edi.sale.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.sale.model.NEDMSAL0010VO;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;


@Repository("nedmsa0010Dao")
public class NEDMSAL0010Dao extends AbstractDAO {
	
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : NEDMSAL0010Dao.java
	 * @since      : 2015. 11. 13.
	 * @author     : 
	 * @version    :
	 * @Locaton    : com.lottemart.epc.edi.sale.dao
	 * @Description : 매출정보-일자별
     * @param 
	 * @return  List<DataMap>
     * @throws 
    */
	public List<NEDMSAL0010VO> selectDayInfo(NEDMSAL0010VO map) throws Exception{
		return (List<NEDMSAL0010VO>)getSqlMapClientTemplate().queryForList("NEDMSAL0010.TSC_DAY-SELECT01",map);
		
	}
	public int selectDayInfoCntSum(NEDMSAL0010VO map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMSAL0010.TSC_DAY-SELECT01CNTSUM",map);
	}
}
