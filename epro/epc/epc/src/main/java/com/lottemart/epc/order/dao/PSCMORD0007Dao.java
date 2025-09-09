
package com.lottemart.epc.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0007VO;

import lcn.module.framework.base.AbstractDAO;

@Repository("PSCMORD0007Dao")
public class PSCMORD0007Dao extends AbstractDAO {

	/*매출정보-상품별 */
	public List<PSCMORD0007VO> selectSaleProductList(Map<String, Object> map) throws Exception {
		return (List<PSCMORD0007VO>) getSqlMapClientTemplate().queryForList("PSCMORD0007.selectSaleProductList", map);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAllOnlineStore() throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0007.selectAllOnlineStore");
	}

	public DataMap selectHodevMallStrYn(PSCMORD0007VO searchVO) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("PSCMORD0007.selectHodevMallStrYn", searchVO);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectCodeList(PSCMORD0007VO searchVO) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0007.selectCodeList", searchVO);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderItemList(DataMap paramMap) throws SQLException {
		List<DataMap> dmList = new ArrayList<DataMap>();

		if (("2").equals(paramMap.getString("searchType"))) {
			dmList = (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0007.selectSaleItemList", paramMap);
		} else {
			dmList = (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0007.selectOrderItemList", paramMap);
		}

		return dmList;
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderItemListExcel(DataMap paramMap) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0007.selectOrderItemListExcel", paramMap);
	}

}
