package com.lottemart.epc.edi.sale.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.sale.model.NEDMSAL0030VO;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;


@Repository("nedmsa0030Dao")
public class NEDMSAL0030Dao extends AbstractDAO {
	
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : NEDMSAL0030Dao.java
	 * @since      : 2015. 11. 13.
	 * @author     : 
	 * @version    :
	 * @Locaton    : com.lottemart.epc.edi.sale.dao
	 * @Description : 매출정보-상품별
     * @param 
	 * @return  List<DataMap>
     * @throws 
    */
	/*매출정보-상품별 */
	public List<NEDMSAL0030VO> selectProductInfo(NEDMSAL0030VO map) throws Exception{
		return (List<NEDMSAL0030VO>)getSqlMapClientTemplate().queryForList("NEDMSAL0030.TSC_PRODUCT-SELECT01",map);
	}
	
}
