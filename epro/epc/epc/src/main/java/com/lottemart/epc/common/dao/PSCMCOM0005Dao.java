package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0005VO;

@Repository("pscmcom0005Dao")
public class PSCMCOM0005Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> getTetCodeList(PSCMCOM0005VO vo) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMCOM0005.getTetCodeList", vo);
	}

	public DataMap selectOrderItemCount(PSCMCOM0005VO vo) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("PSCMCOM0005.selectOrderItemCount", vo);
	}

	public DataMap selectHodecoInfoCount(PSCMCOM0005VO vo) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("PSCMCOM0005.selectHodecoInfoCount", vo);
	}

	public void updatePartnerFirmsOrderItem(PSCMCOM0005VO vo) throws SQLException {
		getSqlMapClientTemplate().update("PSCMCOM0005.updatePartnerFirmsOrderItem", vo);
	}

	public void updatePartnerFirmsDeliMst(PSCMCOM0005VO vo) throws SQLException {
		getSqlMapClientTemplate().update("PSCMCOM0005.updatePartnerFirmsDeliMst", vo);
	}

	public int selectPartnerFirmsHodecoInfoCnt(PSCMCOM0005VO vo) throws SQLException {
		return (Integer) getSqlMapClientTemplate().queryForObject("PSCMCOM0005.selectPartnerFirmsHodecoInfoCnt", vo);
	}

	public void updatePartnerFirmsHodecoInfo(PSCMCOM0005VO vo) throws SQLException {
		getSqlMapClientTemplate().update("PSCMCOM0005.updatePartnerFirmsHodecoInfo", vo);
	}

	public void insertPartnerFirmsHodecoInfo(PSCMCOM0005VO vo) throws SQLException {
		getSqlMapClientTemplate().update("PSCMCOM0005.insertPartnerFirmsHodecoInfo", vo);
	}

	public DataMap selectDeliveryVendor(PSCMCOM0005VO vo) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("PSCMCOM0005.selectDeliveryVendor", vo);
	}

	public Integer selectDeliveryStatusReverceCnt(PSCMCOM0005VO vo) throws SQLException {
		return (Integer) getSqlMapClientTemplate().queryForObject("PSCMCOM0005.selectDeliveryStatusReverceCnt",vo);
	}

}
