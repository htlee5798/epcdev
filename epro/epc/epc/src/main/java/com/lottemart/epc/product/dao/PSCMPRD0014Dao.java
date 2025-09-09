/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0014VO;

@Repository("pscmprd0014Dao")
public class PSCMPRD0014Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectStoreCombo() throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMPRD0014.selectStoreCombo");
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectOutOfStockCount(DataMap paramMap) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMPRD0014.selectOutOfStockCount", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<PSCMPRD0014VO> selectOutOfStockList(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0014VO>) getSqlMapClientTemplate().queryForList("PSCMPRD0014.selectOutOfStockList", paramMap);
	}

	public int updateOutOfStockProd(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("PSCMPRD0014.updateOutOfStockProd", paramMap);
	}

	public int updateOutOfStockItem(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("PSCMPRD0014.updateOutOfStockItem", paramMap);
	}

	public int updateEntpProdCondDeli(DataMap paramMap) {
		return getSqlMapClientTemplate().update("PSCMPRD0014.updateEntpProdCondDeli", paramMap);
	}

	public int updateOutOfStockItemMst(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("PSCMPRD0014.updateOutOfStockItemMst", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<PSCMPRD0014VO> selectOutOfStockListExcel(PSCMPRD0014VO pscmprd0014VO) throws SQLException {
		return (List<PSCMPRD0014VO>) getSqlMapClientTemplate().queryForList("PSCMPRD0014.selectOutOfStockListExcel", pscmprd0014VO);
	}

	// 20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통 조건 문구가 보여서 추가 처리)
	public List<DataMap> selectDeliVendorInfo(DataMap paramMap) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMPRD0014.selectDeliVendorInfo", paramMap);
	}
}
