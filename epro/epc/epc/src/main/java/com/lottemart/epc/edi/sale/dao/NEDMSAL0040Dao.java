package com.lottemart.epc.edi.sale.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.sale.model.NEDMSAL0040VO;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;


@Repository("nedmsa0040Dao")
public class NEDMSAL0040Dao extends AbstractDAO {
	
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : NEDMSAL0040Dao.java
	 * @since      : 2011. 12. 6.
	 * @author     : 
	 * @version    :
	 * @Locaton    : com.lottemart.epc.edi.sale.dao
	 * @Description : 매출정보-상품상세
     * @param 
	 * @return  List<DataMap>
     * @throws 
    */
	/*매출정보-상품상세 */
	public List<NEDMSAL0040VO> selectProductDetailInfo(NEDMSAL0040VO map) throws Exception{
		return (List<NEDMSAL0040VO>)getSqlMapClientTemplate().queryForList("NEDMSAL0040.TSC_PRODUCT_DETAIL-SELECT01",map);
	}
	
}
