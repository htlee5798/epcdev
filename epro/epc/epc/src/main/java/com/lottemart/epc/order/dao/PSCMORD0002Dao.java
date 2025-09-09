
package com.lottemart.epc.order.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0002VO;

@Repository("PSCMORD0002Dao")
public class PSCMORD0002Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectProductSaleSumList(PSCMORD0002VO searchVO) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0002.selectProductSaleSumList", searchVO);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectProductSaleSumListExcel(PSCMORD0002VO searchVO) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0002.selectProductSaleSumListExcel", searchVO);
	}

}
