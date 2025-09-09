package com.lottemart.epc.edi.sale.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;


@Repository("pedmsal0000Dao")
public class PEDMSAL0000Dao extends AbstractDAO {
	
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : PEDMSAL0000Dao.java
	 * @since      : 2011. 12. 6.
	 * @author     : 
	 * @version    :
	 * @Locaton    : com.lottemart.epc.edi.sale.dao
	 * @Description : 매출정보-일자별
     * @param 
	 * @return  List<DataMap>
     * @throws 
    */
	public List<PEDMSAL0000VO> selectDayInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMSAL0000VO>)getSqlMapClientTemplate().queryForList("PEDMSAL0000.TSC_DAY-SELECT01",map);
		
	}
	
	/*매출정보-점포별 */
	public List<PEDMSAL0000VO> selectStoreInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMSAL0000VO>)getSqlMapClientTemplate().queryForList("PEDMSAL0000.TSC_STORE-SELECT01",map);
	}
	
	/*매출정보-상품별 */
	public List<PEDMSAL0000VO> selectProductInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMSAL0000VO>)getSqlMapClientTemplate().queryForList("PEDMSAL0000.TSC_PRODUCT-SELECT01",map);
	}
	
	/*매출정보-상품상세 */
	public List<PEDMSAL0000VO> selectProductDetailInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMSAL0000VO>)getSqlMapClientTemplate().queryForList("PEDMSAL0000.TSC_PRODUCT_DETAIL-SELECT01",map);
	}
	
}
