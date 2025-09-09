
package com.lottemart.epc.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.order.model.PSCMORD0007VO;

import lcn.module.framework.base.AbstractDAO;

@Repository("PSCMORD0008Dao")
public class PSCMORD0008Dao extends AbstractDAO {

	/*매출정보-상품별 */
	public List<PSCMORD0007VO> selectSaleProductDetailList(Map<String, Object> map) throws Exception {
		return (List<PSCMORD0007VO>) getSqlMapClientTemplate().queryForList("PSCMORD0008.selectSaleProductDetailList", map);
	}

}
